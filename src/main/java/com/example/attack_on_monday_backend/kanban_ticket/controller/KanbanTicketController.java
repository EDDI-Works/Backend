package com.example.attack_on_monday_backend.kanban_ticket.controller;

import com.example.attack_on_monday_backend.kanban_ticket.controller.request_form.CreateKanbanTicketRequestForm;
import com.example.attack_on_monday_backend.kanban_ticket.controller.response_form.CreateKanbanTicketResponseForm;
import com.example.attack_on_monday_backend.kanban_ticket.service.KanbanTicketService;
import com.example.attack_on_monday_backend.kanban_ticket.service.response.CreateKanbanTicketResponse;
import com.example.attack_on_monday_backend.redis_cache.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/kanban-ticket")
public class KanbanTicketController {

    final private KanbanTicketService kanbanTicketService;
    final private RedisCacheService redisCacheService;

    @PostMapping("/register")
    public CreateKanbanTicketResponseForm registerKanbanTicket (
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody CreateKanbanTicketRequestForm createKanbanTicketRequestForm) {

        log.info("registerKanbanTicket() -> {}", createKanbanTicketRequestForm);
        log.info("authorizationHeader -> {}", authorizationHeader);

        String userToken = authorizationHeader.replace("Bearer ", "").trim();

        Long accountId = redisCacheService.getValueByKey(userToken, Long.class);
        if (accountId == null) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        CreateKanbanTicketResponse response = kanbanTicketService.register(createKanbanTicketRequestForm.toCreateKanbanTicketRequest(accountId));

        return CreateKanbanTicketResponseForm.from(response);
    }
}
