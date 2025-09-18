package com.example.attack_on_monday_backend.agile_board.controller.request_form;

import com.example.attack_on_monday_backend.agile_board.service.request.CreateAgileBoardRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CreateAgileBoardRequestForm {
    final private Long projectId;
    final private String title;

    public CreateAgileBoardRequest toCreateAgileBoardRequest(Long accountId) {
        return new CreateAgileBoardRequest(projectId, title, accountId);
    }
}
