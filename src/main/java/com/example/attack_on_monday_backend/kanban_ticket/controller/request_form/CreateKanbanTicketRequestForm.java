package com.example.attack_on_monday_backend.kanban_ticket.controller.request_form;

import com.example.attack_on_monday_backend.kanban_ticket.service.request.CreateKanbanTicketRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CreateKanbanTicketRequestForm {
    final private Long agileBoardId;
    final private String title;

    public CreateKanbanTicketRequest toCreateKanbanTicketRequest(Long accountId) {
        return new CreateKanbanTicketRequest(agileBoardId, title, accountId);
    }
}
