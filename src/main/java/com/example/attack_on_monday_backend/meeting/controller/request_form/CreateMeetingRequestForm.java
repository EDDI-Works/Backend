package com.example.attack_on_monday_backend.meeting.controller.request_form;

import com.example.attack_on_monday_backend.meeting.service.request.CreateMeetingRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateMeetingRequestForm {
    private Long projectId;
    private String title;
    private Boolean allDay;
    private String start; // ISO-8601
    private String end;
    private Long templateId;
    private Integer templateVersion;
    private List<Long> teamIds;               // 1개면 단일팀, 2개 이상이면 멀티
    private List<Long> participantAccountIds; // 선택된 팀의 참여자 계정들

    public CreateMeetingRequest toCreateMeetingRequest(Long creatorId){
        return new CreateMeetingRequest(
                creatorId, projectId, title,
                allDay, start, end,
                templateId, templateVersion,
                teamIds, participantAccountIds
        );
    }

}
