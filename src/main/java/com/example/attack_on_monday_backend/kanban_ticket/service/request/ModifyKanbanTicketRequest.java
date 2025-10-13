package com.example.attack_on_monday_backend.kanban_ticket.service.request;

import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class ModifyKanbanTicketRequest {
    private final Long ticketId;
    private final String title;
    private final String description;
    private final String status;
    private final String priority;
    private final String domain;
}
