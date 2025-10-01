package com.example.attack_on_monday_backend.meeting.service.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class UpdateMeetingRequest {
    private Long accountId;

    private String title;
    private Boolean allDay;
    private String start;
    private String end;

    private List<Long> teamIds;
    private List<Long> participantAccountIds;

    private String content;

    private Long meetingVersion;
    private Long noteVersion;


}
