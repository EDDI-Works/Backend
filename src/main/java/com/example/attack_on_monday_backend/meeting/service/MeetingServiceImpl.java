package com.example.attack_on_monday_backend.meeting.service;

import com.example.attack_on_monday_backend.account_profile.entity.AccountProfile;
import com.example.attack_on_monday_backend.account_profile.repository.AccountProfileRepository;
import com.example.attack_on_monday_backend.meeting.entity.*;
import com.example.attack_on_monday_backend.meeting.repository.MeetingNoteRepository;
import com.example.attack_on_monday_backend.meeting.repository.MeetingParticipantRepository;
import com.example.attack_on_monday_backend.meeting.repository.MeetingParticipateTeamRepository;
import com.example.attack_on_monday_backend.meeting.repository.MeetingRepository;
import com.example.attack_on_monday_backend.meeting.service.request.CreateMeetingRequest;
import com.example.attack_on_monday_backend.meeting.service.request.UpdateMeetingRequest;
import com.example.attack_on_monday_backend.meeting.service.response.CreateMeetingResponse;
import com.example.attack_on_monday_backend.project.entity.Project;
import com.example.attack_on_monday_backend.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    final private MeetingRepository meetingRepository;
    final private MeetingNoteRepository meetingNoteRepository;
    final private MeetingParticipantRepository meetingParticipantRepository;
    final private MeetingParticipateTeamRepository meetingParticipateTeamRepository;
    final private ProjectRepository projectRepository;
    final private AccountProfileRepository accountProfileRepository;

    @Override
    public CreateMeetingResponse create(CreateMeetingRequest request) {

        log.info("CreateMeetingRequest= {}", request);

        // 검증, 로딩
        if (request.getProjectId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "projectId is required");
        }

        AccountProfile creator = accountProfileRepository.findById(request.getCreatorId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "프로젝트를 찾을 수 없습니다."));

        LocalDateTime start = request.startAsLdt();
        LocalDateTime end = request.endAsLdt();
        boolean allDay = Boolean.TRUE.equals(request.getAllDay());
        if (!allDay && (start == null || end == null || start.isBefore(end))) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "시작 시간이 종료 시간보다 작아야 합니다.");
        }

        // 미팅 저장
        Meeting meeting = request.toMeeting(creator, project);
        meetingRepository.save(meeting);

        // 팀 연결 (팀이 여러개이면 participate 테이블 채움)
        List<Long> teamIds = request.getTeamIds() == null ? List.of() : request.getTeamIds().stream().distinct().toList();
        if (teamIds.size() >= 2) {
            for (Long teamId : teamIds) {
                MeetingParticipateTeam participateTeam = new MeetingParticipateTeam();
                participateTeam.setMeeting(meeting);
                participateTeam.setTeamId(teamId);
                meetingParticipateTeamRepository.save(participateTeam);
            }
        }

        // Note(본문) 생성
        MeetingNote note = new MeetingNote();
        note.setMeeting(meeting);
        note.setContent("### Notes\n\n");
        meetingNoteRepository.save(note);

        // 참여자 등록
        // - 생성자 저장
        MeetingParticipant owner =  new MeetingParticipant();
        owner.setMeeting(meeting);
        owner.setAccount(creator);
        owner.setParticipantRole(ParticipantRole.OWNER);
        meetingParticipantRepository.save(owner);

        // - 참여자 저장
        if (request.getParticipantAccountIds() != null) {
            for (Long aid : request.getParticipantAccountIds().stream().distinct().toList()) {
                if (Objects.equals(aid, creator.getId())) continue;
                AccountProfile acc = accountProfileRepository.findById(aid)
                        .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "유효하지 않은 참여자: " + aid));

                MeetingParticipant participant = new MeetingParticipant();
                participant.setMeeting(meeting);
                participant.setAccount(acc);
                participant.setParticipantRole(ParticipantRole.MEMBER);
                meetingParticipantRepository.save(participant);
            }
        }

        int participantCount = (request.getParticipantAccountIds() == null ? 0 :  request.getParticipantAccountIds().size())+1;

        return CreateMeetingResponse.from(meeting, note, participantCount);
    }

}
