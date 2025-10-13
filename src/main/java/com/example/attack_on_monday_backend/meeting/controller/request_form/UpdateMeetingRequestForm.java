package com.example.attack_on_monday_backend.meeting.controller.request_form;

import com.example.attack_on_monday_backend.meeting.service.request.UpdateMeetingRequest;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class UpdateMeetingRequestForm {
    private String title;
    private Boolean allDay;
    private String start;
    private String end;
    private List<Long> teamIds;
    private List<Long> participantAccountIds;
    private String content;
    private Long meetingVersion;
    private Long noteVersion;

    public UpdateMeetingRequest toUpdateMeetingRequest(Long accountId){
        return new UpdateMeetingRequest(
                accountId, title, allDay, start, end,
                teamIds, participantAccountIds, content,
                meetingVersion,noteVersion
        );
    }
}
