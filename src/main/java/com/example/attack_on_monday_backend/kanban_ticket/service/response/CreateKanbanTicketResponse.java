package com.example.attack_on_monday_backend.kanban_ticket.service.response;

import com.example.attack_on_monday_backend.kanban_ticket.entity.KanbanTicket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateKanbanTicketResponse {
    private final Long id;
    private final String title;
    private final String description;
    private final String status;
    private final String priority;
    private final String domain;
    private final String writerNickname;
    private final LocalDateTime createDate;
    private final LocalDateTime updateDate;
    private final Long agileBoardId;

    public static CreateKanbanTicketResponse from(KanbanTicket kanbanTicket) {
        return new CreateKanbanTicketResponse(
                kanbanTicket.getId(),
                kanbanTicket.getTitle(),
                kanbanTicket.getDescription(),
                kanbanTicket.getStatus() != null ? kanbanTicket.getStatus().name() : null,
                kanbanTicket.getPriority() != null ? kanbanTicket.getPriority().name() : null,
                kanbanTicket.getDomain(),
                kanbanTicket.getWriter().getNickname(),
                kanbanTicket.getCreateDate(),
                kanbanTicket.getUpdateDate(),
                kanbanTicket.getAgileBoard().getId()
        );
    }
}
