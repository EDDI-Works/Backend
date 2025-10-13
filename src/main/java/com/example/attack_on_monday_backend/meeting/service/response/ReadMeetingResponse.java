package com.example.attack_on_monday_backend.meeting.service.response;

import com.example.attack_on_monday_backend.meeting.entity.Meeting;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@ToString
@RequiredArgsConstructor
public class ReadMeetingResponse {
    // 기본 정보
    final private Long meetingId;
    final private String publicId;
    final private String title;
    final private Boolean allDay;
    final private String start;
    final private String end;
    // 리스트
    final private List<Map<String, Object>> participantList;
    final private List<Map<String, Object>> teamList;
    // 상세(본문, 버전)
    final private String noteContent;
    final private Long meetingVersion;
    // 생성자/타임스탬프
    final private Long creatorAccountId;     // 생성자 ID
    final private String creatorNickname;    // 생성자 표시명(닉네임/이메일 등)
    final private String createdAt;   // 생성일
    final private String updatedAt;   // 업데이트일

    public static ReadMeetingResponse from(
            Meeting meeting,
            List<Map<String, Object>> participantList,
            List<Map<String, Object>> teamList,
            String noteContent
    ){
        String startStr  = meeting.getStartTime()  == null ? null : meeting.getStartTime().toString();
        String endStr    = meeting.getEndTime()    == null ? null : meeting.getEndTime().toString();
        String created   = meeting.getCreatedAt()  == null ? null : meeting.getCreatedAt().toString();
        String updated   = meeting.getUpdatedAt()  == null ? null : meeting.getUpdatedAt().toString();

        Long creatorId   = meeting.getCreator() == null ? null : meeting.getCreator().getId();
        String creatorName = meeting.getCreator() == null ? null : meeting.getCreator().getNickname();

        return new ReadMeetingResponse(
                meeting.getId(),
                meeting.getPublicId(),
                meeting.getTitle(),
                meeting.isAllDay(),
                startStr,
                endStr,
                participantList,
                teamList,
                (noteContent == null ? "" : noteContent),
                meeting.getVersion(),
                creatorId,
                creatorName,
                created,
                updated
        );
    }

}
