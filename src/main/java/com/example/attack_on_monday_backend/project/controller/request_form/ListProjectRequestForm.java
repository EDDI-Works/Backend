package com.example.attack_on_monday_backend.project.controller.request_form;

import com.example.attack_on_monday_backend.project.service.request.ListProjectRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ListProjectRequestForm {
    final private int page;
    final private int perPage;

    public ListProjectRequest toListProjectRequest() {
        return new ListProjectRequest(page, perPage);
    }
}
