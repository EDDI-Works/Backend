package com.example.attack_on_monday_backend.team.controller.response_form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamListResponseForm {
    private List<TeamInfoResponseForm> teams;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamInfoResponseForm {
        private Long id;
        private String name;
        private List<ProjectInfoResponseForm> projects;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectInfoResponseForm {
        private Long id;
        private String name;
    }
}
