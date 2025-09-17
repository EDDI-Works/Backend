package com.example.attack_on_monday_backend.project.service.request;

import com.example.attack_on_monday_backend.account_profile.entity.AccountProfile;
import com.example.attack_on_monday_backend.project.entity.Project;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CreateProjectRequest {
    final private String title;
    final private Long accountId;

    public Project toProject(AccountProfile writer) {
        return new Project(title, writer);
    }
}
