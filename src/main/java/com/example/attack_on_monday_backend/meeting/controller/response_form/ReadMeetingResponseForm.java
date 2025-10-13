package com.example.attack_on_monday_backend.meeting.controller.response_form;

import com.example.attack_on_monday_backend.meeting.service.response.ReadMeetingResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ReadMeetingResponseForm {
    // 기본 정보
    private final Long   meetingId;
    private final String publicId;
    private final String title;
    private final Boolean allDay;
    private final String start;
    private final String end;

    // 생성자/타임스탬프
    private final Long   creatorAccountId;
    private final String creatorNickname;
    private final String createdAt;
    private final String updatedAt;

    // 상세/버전
    private final String noteContent;
    private final Long   meetingVersion;

    // 참가자/팀 (id+name 맵 리스트 형태 유지)
    private final List<Map<String, Object>> participantList;
    private final List<Map<String, Object>> teamList;

    public static ReadMeetingResponseForm from(ReadMeetingResponse r) {
        return new ReadMeetingResponseForm(
                r.getMeetingId(),
                r.getPublicId(),
                r.getTitle(),
                r.getAllDay(),
                r.getStart(),
                r.getEnd(),
                r.getCreatorAccountId(),
                r.getCreatorNickname(),
                r.getCreatedAt(),
                r.getUpdatedAt(),
                r.getNoteContent() == null ? "" : r.getNoteContent(),
                r.getMeetingVersion(),
                r.getParticipantList(),
                r.getTeamList()
        );
    }
}
