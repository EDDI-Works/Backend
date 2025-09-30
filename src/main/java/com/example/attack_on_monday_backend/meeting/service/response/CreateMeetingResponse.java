package com.example.attack_on_monday_backend.meeting.service.response;

import com.example.attack_on_monday_backend.meeting.entity.Meeting;
import com.example.attack_on_monday_backend.meeting.entity.MeetingNote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMeetingResponse {
    private Long meetingId;
    private Long noteId;
    private String publicId;
    private int participantCount;

    public static CreateMeetingResponse from(Meeting meeting, MeetingNote meetingNote, int participantCount) {
        return new CreateMeetingResponse(
                meeting.getId(),
                meetingNote.getId(),
                meeting.getPublicId(),
                participantCount
        );
    }
}
