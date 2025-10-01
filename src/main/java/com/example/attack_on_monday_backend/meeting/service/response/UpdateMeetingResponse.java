package com.example.attack_on_monday_backend.meeting.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class UpdateMeetingResponse {
    private String publicId;
    private Long meetingId;
    private Long meetingVersion;
    private Long noteVersion;
}
