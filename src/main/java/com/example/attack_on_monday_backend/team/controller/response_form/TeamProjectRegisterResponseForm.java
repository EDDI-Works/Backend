package com.example.attack_on_monday_backend.team.controller.response_form;

import com.example.attack_on_monday_backend.project.service.response.CreateProjectResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamProjectRegisterResponseForm {
    private final Long projectId;
    private final String title;
    private final Long teamId;
    private final String writerNickname;

    public static TeamProjectRegisterResponseForm from(CreateProjectResponse response) {
        return TeamProjectRegisterResponseForm.builder()
                .projectId(response.getProjectId())
                .title(response.getTitle())
                .teamId(response.getTeamId())
                .writerNickname(response.getWriterNickname())
                .build();
    }
}
