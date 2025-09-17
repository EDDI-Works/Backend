package com.example.attack_on_monday_backend.project.controller.response_form;

import com.example.attack_on_monday_backend.project.service.response.ListProjectResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class ListProjectResponseForm {
    private final List<Map<String, Object>> projectList;
    private final Long totalItems;
    private final Integer totalPages;

    public static ListProjectResponseForm from(List<ListProjectResponse> projectListResponses, Long totalItems, Integer totalPages) {
        List<Map<String, Object>> combinedProjectList = projectListResponses.stream()
                .flatMap(response -> response.getProjectListWithNicknames().stream())
                .collect(Collectors.toList());

        return new ListProjectResponseForm(combinedProjectList, totalItems, totalPages);
    }
}
