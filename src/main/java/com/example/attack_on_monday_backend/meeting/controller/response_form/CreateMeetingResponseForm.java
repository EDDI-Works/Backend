package com.example.attack_on_monday_backend.meeting.controller.response_form;

import com.example.attack_on_monday_backend.meeting.service.response.CreateMeetingResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateMeetingResponseForm {
    private Long meetingId;
    private Long noteId;
    private String noteContent;
    private String publicId;
    private int participantCount;

    public static CreateMeetingResponseForm from(CreateMeetingResponse response) {
        return new CreateMeetingResponseForm(
                response.getMeetingId(),
                response.getNoteId(),
                response.getNoteContent(),
                response.getPublicId(),
                response.getParticipantCount()
        );
    }
}
