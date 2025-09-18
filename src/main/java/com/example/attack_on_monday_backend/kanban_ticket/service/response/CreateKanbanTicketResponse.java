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
    private final String writerNickname;
    private final LocalDateTime createDate;

    private final Long agileBoardId;

    public static CreateKanbanTicketResponse from(KanbanTicket kanbanTicket) {
        return new CreateKanbanTicketResponse(
                kanbanTicket.getId(),
                kanbanTicket.getTitle(),
                kanbanTicket.getWriter().getNickname(),
                kanbanTicket.getCreateDate(),
                kanbanTicket.getAgileBoard().getId()
        );
    }
}
