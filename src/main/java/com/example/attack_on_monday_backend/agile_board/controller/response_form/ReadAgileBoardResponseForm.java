package com.example.attack_on_monday_backend.agile_board.controller.response_form;

import com.example.attack_on_monday_backend.agile_board.service.response.ReadAgileBoardResponse;
import com.example.attack_on_monday_backend.project.controller.response_form.ReadProjectResponseForm;
import com.example.attack_on_monday_backend.project.service.response.ReadProjectResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ReadAgileBoardResponseForm {

    private final Long agileBoardId;
    private final String title;
    private final String writerNickname;
    private final List<Map<String, Object>> kanbanTicketList;
    private final long totalItems;
    private final int totalPages;

    public static ReadAgileBoardResponseForm from(ReadAgileBoardResponse response) {
        return new ReadAgileBoardResponseForm(
                response.getAgileBoardId(),
                response.getTitle(),
                response.getWriterNickname(),
                response.getKanbanTicketList(),
                response.getTotalItems(),
                response.getTotalPages()
        );
    }
}
