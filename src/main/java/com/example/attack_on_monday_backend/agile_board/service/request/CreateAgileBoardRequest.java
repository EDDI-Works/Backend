package com.example.attack_on_monday_backend.agile_board.service.request;

import com.example.attack_on_monday_backend.account_profile.entity.AccountProfile;
import com.example.attack_on_monday_backend.agile_board.entity.AgileBoard;
import com.example.attack_on_monday_backend.project.entity.Project;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CreateAgileBoardRequest {
    final private Long projectId;
    final private String title;
    final private Long accountId;

    public AgileBoard toAgileBoard(AccountProfile writer, Project project) {
        return new AgileBoard(title, writer, project);
    }
}
