package com.example.attack_on_monday_backend.meeting.controller.response_form;

import com.example.attack_on_monday_backend.meeting.service.response.UpdateMeetingResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class UpdateMeetingResponseForm {
    private String publicId;
    private Long meetingId;
    private Long meetingVersion;
    private Long noteVersion;

    public static UpdateMeetingResponseForm from(UpdateMeetingResponse res) {
        return new UpdateMeetingResponseForm(
                res.getPublicId(),
                res.getMeetingId(),
                res.getMeetingVersion(),
                res.getNoteVersion()
        );
    }
}
