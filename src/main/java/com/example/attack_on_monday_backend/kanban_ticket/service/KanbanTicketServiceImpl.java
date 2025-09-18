package com.example.attack_on_monday_backend.kanban_ticket.service;

import com.example.attack_on_monday_backend.account.entity.Account;
import com.example.attack_on_monday_backend.account.repository.AccountRepository;
import com.example.attack_on_monday_backend.account_profile.entity.AccountProfile;
import com.example.attack_on_monday_backend.account_profile.repository.AccountProfileRepository;
import com.example.attack_on_monday_backend.agile_board.entity.AgileBoard;
import com.example.attack_on_monday_backend.agile_board.repository.AgileBoardRepository;
import com.example.attack_on_monday_backend.kanban_ticket.entity.KanbanTicket;
import com.example.attack_on_monday_backend.kanban_ticket.repository.KanbanTicketRepository;
import com.example.attack_on_monday_backend.kanban_ticket.service.request.CreateKanbanTicketRequest;
import com.example.attack_on_monday_backend.kanban_ticket.service.response.CreateKanbanTicketResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
