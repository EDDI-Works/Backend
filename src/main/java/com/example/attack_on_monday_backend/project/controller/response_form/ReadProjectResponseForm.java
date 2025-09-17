package com.example.attack_on_monday_backend.project.controller.response_form;

import com.example.attack_on_monday_backend.project.service.response.ReadProjectResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ReadProjectResponseForm {

    private final Long projectId;
    private final String title;
    private final String writerNickname;
    private final List<Map<String, Object>> agileBoardList;
    private final long totalItems;
    private final int totalPages;

    public static ReadProjectResponseForm from(ReadProjectResponse response) {
        return new ReadProjectResponseForm(
                response.getProjectId(),
                response.getTitle(),
                response.getWriterNickname(),
                response.getAgileBoardList(),
                response.getTotalItems(),
                response.getTotalPages()
        );
    }
}
