package com.example.attack_on_monday_backend.project.controller.request_form;

import com.example.attack_on_monday_backend.project.service.request.CreateProjectRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CreateProjectRequestForm {
    final private String title;

    public CreateProjectRequest toCreateProjectRequest(Long accountId) {
        return new CreateProjectRequest(title, accountId);
    }
}
