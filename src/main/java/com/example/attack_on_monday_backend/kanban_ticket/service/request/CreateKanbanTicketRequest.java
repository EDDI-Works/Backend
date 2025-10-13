package com.example.attack_on_monday_backend.kanban_ticket.service.request;

import com.example.attack_on_monday_backend.account_profile.entity.AccountProfile;
import com.example.attack_on_monday_backend.agile_board.entity.AgileBoard;
import com.example.attack_on_monday_backend.kanban_ticket.entity.KanbanTicket;
import com.example.attack_on_monday_backend.kanban_ticket.entity.Priority;
import com.example.attack_on_monday_backend.kanban_ticket.entity.Status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CreateKanbanTicketRequest {
    final private Long agileBoardId;
    final private String title;
    final private String description;
    final private Status status;
    final private Priority priority;
    final private String domain;
    final private Long accountId;

    public KanbanTicket toKanbanTicket(AccountProfile writer, AgileBoard agileBoard) {
        Status ticketStatus = (status != null) ? status : Status.BACKLOG;
        Priority ticketPriority = (priority != null) ? priority : Priority.MEDIUM;
        return new KanbanTicket(title, description, ticketStatus, ticketPriority, domain, writer, agileBoard);
    }
}
