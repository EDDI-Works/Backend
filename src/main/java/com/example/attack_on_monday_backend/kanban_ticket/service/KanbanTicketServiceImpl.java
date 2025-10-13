package com.example.attack_on_monday_backend.kanban_ticket.service;

import com.example.attack_on_monday_backend.account.entity.Account;
import com.example.attack_on_monday_backend.account.repository.AccountRepository;
import com.example.attack_on_monday_backend.account_profile.entity.AccountProfile;
import com.example.attack_on_monday_backend.account_profile.repository.AccountProfileRepository;
import com.example.attack_on_monday_backend.agile_board.entity.AgileBoard;
import com.example.attack_on_monday_backend.agile_board.repository.AgileBoardRepository;
import com.example.attack_on_monday_backend.kanban_ticket.entity.KanbanTicket;
import com.example.attack_on_monday_backend.kanban_ticket.entity.Priority;
import com.example.attack_on_monday_backend.kanban_ticket.entity.Status;
import com.example.attack_on_monday_backend.kanban_ticket.repository.KanbanTicketRepository;
import com.example.attack_on_monday_backend.kanban_ticket.service.request.CreateKanbanTicketRequest;
import com.example.attack_on_monday_backend.kanban_ticket.service.request.ModifyKanbanTicketRequest;
import com.example.attack_on_monday_backend.kanban_ticket.service.response.CreateKanbanTicketResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KanbanTicketServiceImpl implements KanbanTicketService {

    final private AccountRepository accountRepository;
    final private AccountProfileRepository accountProfileRepository;
    final private AgileBoardRepository agileBoardRepository;
    final private KanbanTicketRepository kanbanTicketRepository;

    @Override
    public CreateKanbanTicketResponse register(CreateKanbanTicketRequest createKanbanTicketRequest) {
        log.info("accountId: {}", createKanbanTicketRequest.getAccountId());

        Account account = accountRepository.findById(createKanbanTicketRequest.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account 존재하지 않음"));

        log.info("account: {}", account);

        AccountProfile accountProfile = accountProfileRepository.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("AccountProfile not found"));

        log.info("account profile: {}", accountProfile);

        AgileBoard agileBoard = agileBoardRepository.findById(createKanbanTicketRequest.getAgileBoardId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        log.info("account project: {}", accountProfile);

        KanbanTicket savedKanbanTicket = kanbanTicketRepository.save(createKanbanTicketRequest.toKanbanTicket(accountProfile, agileBoard));
        return CreateKanbanTicketResponse.from(savedKanbanTicket);
    }

    @Override
    @Transactional
    public CreateKanbanTicketResponse modify(ModifyKanbanTicketRequest modifyKanbanTicketRequest) {
        log.info("modify ticket: {}", modifyKanbanTicketRequest);

        KanbanTicket kanbanTicket = kanbanTicketRepository.findById(modifyKanbanTicketRequest.getTicketId())
                .orElseThrow(() -> new RuntimeException("Kanban Ticket not found"));

        // 티켓 정보 업데이트
        if (modifyKanbanTicketRequest.getTitle() != null) {
            kanbanTicket.setTitle(modifyKanbanTicketRequest.getTitle());
            log.info("Updated title: {}", modifyKanbanTicketRequest.getTitle());
        }
        if (modifyKanbanTicketRequest.getDescription() != null) {
            kanbanTicket.setDescription(modifyKanbanTicketRequest.getDescription());
            log.info("Updated description: {}", modifyKanbanTicketRequest.getDescription());
        }
        if (modifyKanbanTicketRequest.getStatus() != null) {
            try {
                Status statusEnum = Status.valueOf(modifyKanbanTicketRequest.getStatus());
                kanbanTicket.setStatus(statusEnum);
                log.info("Updated status: {} -> {}", modifyKanbanTicketRequest.getStatus(), statusEnum);
            } catch (IllegalArgumentException e) {
                log.error("Invalid status value: {}", modifyKanbanTicketRequest.getStatus(), e);
            }
        }
        if (modifyKanbanTicketRequest.getPriority() != null) {
            try {
                Priority priorityEnum = Priority.valueOf(modifyKanbanTicketRequest.getPriority());
                kanbanTicket.setPriority(priorityEnum);
                log.info("Updated priority: {} -> {}", modifyKanbanTicketRequest.getPriority(), priorityEnum);
            } catch (IllegalArgumentException e) {
                log.error("Invalid priority value: {}", modifyKanbanTicketRequest.getPriority(), e);
            }
        }
        if (modifyKanbanTicketRequest.getDomain() != null) {
            kanbanTicket.setDomain(modifyKanbanTicketRequest.getDomain());
            log.info("Updated domain: {}", modifyKanbanTicketRequest.getDomain());
        }

        log.info("Saving ticket with status: {}, priority: {}", kanbanTicket.getStatus(), kanbanTicket.getPriority());
        KanbanTicket updatedTicket = kanbanTicketRepository.save(kanbanTicket);
        return CreateKanbanTicketResponse.from(updatedTicket);
    }

    @Override
    @Transactional
    public void delete(Long ticketId) {
        log.info("delete ticket: {}", ticketId);
        
        KanbanTicket kanbanTicket = kanbanTicketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Kanban Ticket not found"));
        
        kanbanTicketRepository.delete(kanbanTicket);
    }

}
