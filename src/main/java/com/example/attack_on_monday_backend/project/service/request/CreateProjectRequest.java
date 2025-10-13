package com.example.attack_on_monday_backend.project.service.request;

import com.example.attack_on_monday_backend.account_profile.entity.AccountProfile;
import com.example.attack_on_monday_backend.project.entity.Project;
import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class CreateProjectRequest {
    private final String title;
    private final Long accountId;
    private final Long teamId;

    public CreateProjectRequest(String title, Long accountId) {
        this.title = title;
        this.accountId = accountId;
        this.teamId = null;
    }

    public Project toProject(AccountProfile writer) {
        return new Project(title, writer, teamId);
    }
}
