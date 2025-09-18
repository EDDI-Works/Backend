package com.example.attack_on_monday_backend.kanban_ticket.service.request;

import com.example.attack_on_monday_backend.account_profile.entity.AccountProfile;
import com.example.attack_on_monday_backend.agile_board.entity.AgileBoard;
import com.example.attack_on_monday_backend.kanban_ticket.entity.KanbanTicket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CreateKanbanTicketRequest {
    final private Long agileBoardId;
    final private String title;
    final private Long accountId;

    public KanbanTicket toKanbanTicket(AccountProfile writer, AgileBoard agileBoard) {
        return new KanbanTicket(title, writer, agileBoard);
    }
}
