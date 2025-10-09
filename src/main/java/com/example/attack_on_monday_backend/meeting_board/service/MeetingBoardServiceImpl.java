package com.example.attack_on_monday_backend.meeting_board.service;

import com.example.attack_on_monday_backend.meeting.entity.Meeting;
import com.example.attack_on_monday_backend.meeting.repository.MeetingParticipantRepository;
import com.example.attack_on_monday_backend.meeting.repository.MeetingRepository;
import com.example.attack_on_monday_backend.meeting_board.entity.MeetingBoard;
import com.example.attack_on_monday_backend.meeting_board.repository.MeetingBoardRepository;
import com.example.attack_on_monday_backend.meeting_board.service.model.BoardSnapshot;
import com.example.attack_on_monday_backend.meeting_board.service.request.UpsertBoardRequest;
import com.example.attack_on_monday_backend.meeting_board.service.response.BoardResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingBoardServiceImpl implements MeetingBoardService{

    private final MeetingRepository meetingRepository;
    private final MeetingParticipantRepository meetingParticipantRepository;
    private final MeetingBoardRepository meetingBoardRepository;
    private final ObjectMapper objectMapper;

    @Override
    public BoardResponse upsertByPublicId(String publicId, UpsertBoardRequest request) {
        Meeting meeting = meetingRepository.findByPublicId(publicId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 미팅을 찾을 수 없습니다."));

        if (meeting.isLocked()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "잠금 상태입니다.");
        }
        boolean isOwner = meeting.getCreator() != null && meeting.getCreator().getId().equals(request.getAccountId());
        boolean isParticipant = meetingParticipantRepository.existsByMeetingIdAndAccountId(meeting.getId(), request.getAccountId());
        if (!(isOwner || isParticipant)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"수정 권한이 없습니다.");
        }

        String json = writeSnapshot(request.getBoardSnapshot());

        MeetingBoard saved = meetingBoardRepository.findByMeetingId(meeting.getId())
                .map(b -> {
                    b.setTemplate(request.getBoardSnapshot().getTemplate());
                    b.setTitle(request.getBoardSnapshot().getTitle());
                    b.setSnapshotJson(json);
                    return b;
                })
                .orElseGet(() -> {
                    MeetingBoard mb = new MeetingBoard();
                    mb.setMeeting(meeting);
                    mb.setTemplate(request.getBoardSnapshot().getTemplate());
                    mb.setTitle(request.getBoardSnapshot().getTitle());
                    mb.setSnapshotJson(json);
                    return mb;
                });

        meetingBoardRepository.saveAndFlush(saved);
        BoardSnapshot boardSnapshot = readSnapshot(saved.getSnapshotJson());

        return BoardResponse.from(publicId, boardSnapshot, saved.getUpdatedAt());
    }

    // JSON 직렬화/역직렬화
    private String writeSnapshot(BoardSnapshot s){
        try {
            return objectMapper.writeValueAsString(s);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "스냅샷 직렬화 실패", e);
        }
    }

    private BoardSnapshot readSnapshot(String json) {
        try {
            return objectMapper.readValue(json, BoardSnapshot.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "스냅샷 역직렬화 실패", e);
        }
    }

    @Override
    public BoardResponse read(Long accountId, String publicId) {

        Meeting meeting = meetingRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 미팅을 찾을 수 없습니다."));

        boolean isOwner = meeting.getCreator() != null && meeting.getCreator().getId().equals(accountId);
        boolean isParticipant = meetingParticipantRepository.existsByMeetingIdAndAccountId(meeting.getId(), accountId);
        if (!(isOwner || isParticipant)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "조회 권한이 없습니다.");
        }

        MeetingBoard board = meetingBoardRepository.findByMeetingId(meeting.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "보드를 찾을 수 없습니다."));

        BoardSnapshot boardSnapshot = readSnapshot(board.getSnapshotJson());

        return BoardResponse.from(publicId, boardSnapshot, board.getUpdatedAt());
    }
}
