package com.example.attack_on_monday_backend.meeting.service.request;

import com.example.attack_on_monday_backend.account_profile.entity.AccountProfile;
import com.example.attack_on_monday_backend.meeting.entity.Meeting;
import com.example.attack_on_monday_backend.project.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateMeetingRequest {
    // 생성자 id
    private Long creatorId;

    private Long projectId;
    private String title;

    private Boolean allDay;
    private String start;
    private String end;

    // 템플릿/팀/개인 참여자
    private Long templateId;
    private Integer templateVersion;
    private List<Long> teamIds;
    private List<Long> participantAccountIds;


    public Meeting toMeeting(AccountProfile creator, Project project){
        Meeting m = new Meeting();
        m.setProject(project);
        m.setCreator(creator);
        m.setTitle(title == null ? "" : title);

        boolean ad = Boolean.TRUE.equals(allDay);
        m.setAllDay(ad);

        LocalDateTime s = parseISO(start);
        LocalDateTime e = parseISO(end);
        m.setStartTime(s);
        m.setEndTime(ad ? s.plusDays(1) : e);

        if (teamIds != null && !teamIds.isEmpty()) {
            m.setMainTeamId(teamIds.get(0));
        }
        return m;
    }

    public LocalDateTime startAsLdt() {
        return parseISO(start);
    }
    public LocalDateTime endAsLdt()   {
        return parseISO(end);
    }

    private LocalDateTime parseISO(String s) {
        return (s == null || s.isBlank()) ? null : LocalDateTime.parse(s);
    }
}
