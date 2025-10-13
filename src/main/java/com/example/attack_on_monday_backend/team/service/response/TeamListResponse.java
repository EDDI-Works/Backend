package com.example.attack_on_monday_backend.team.service.response;

import com.example.attack_on_monday_backend.team.controller.response_form.TeamListResponseForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamListResponse {
    private List<TeamInfo> teams;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamInfo {
        private Long id;
        private String name;
        private List<ProjectInfo> projects;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectInfo {
        private Long id;
        private String name;
    }

    public TeamListResponseForm toResponseForm() {
        return new TeamListResponseForm(
            teams.stream()
                .map(team -> new TeamListResponseForm.TeamInfoResponseForm(
                    team.getId(),
                    team.getName(),
                    team.getProjects().stream()
                        .map(project -> new TeamListResponseForm.ProjectInfoResponseForm(
                            project.getId(),
                            project.getName()
                        ))
                        .collect(Collectors.toList())
                ))
                .collect(Collectors.toList())
        );
    }
}
