package com.example.attack_on_monday_backend.kanban_ticket.service;

import com.example.attack_on_monday_backend.kanban_ticket.service.request.CreateKanbanTicketRequest;
import com.example.attack_on_monday_backend.kanban_ticket.service.request.ModifyKanbanTicketRequest;
import com.example.attack_on_monday_backend.kanban_ticket.service.response.CreateKanbanTicketResponse;

public interface KanbanTicketService {
    CreateKanbanTicketResponse register(CreateKanbanTicketRequest createKanbanTicketRequest);
    CreateKanbanTicketResponse modify(ModifyKanbanTicketRequest modifyKanbanTicketRequest);
    void delete(Long ticketId);
}
