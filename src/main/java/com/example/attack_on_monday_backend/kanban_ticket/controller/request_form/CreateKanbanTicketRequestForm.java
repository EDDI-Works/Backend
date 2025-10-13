package com.example.attack_on_monday_backend.kanban_ticket.controller.request_form;

import com.example.attack_on_monday_backend.kanban_ticket.entity.Priority;
import com.example.attack_on_monday_backend.kanban_ticket.entity.Status;
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
    final private String description;
    final private Status status;
    final private Priority priority;
    final private String domain;

    public CreateKanbanTicketRequest toCreateKanbanTicketRequest(Long accountId) {
        return new CreateKanbanTicketRequest(agileBoardId, title, description, status, priority, domain, accountId);
    }
}
