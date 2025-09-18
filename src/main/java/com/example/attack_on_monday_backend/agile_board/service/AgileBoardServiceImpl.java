package com.example.attack_on_monday_backend.agile_board.service;

import com.example.attack_on_monday_backend.account.entity.Account;
import com.example.attack_on_monday_backend.account.repository.AccountRepository;
import com.example.attack_on_monday_backend.account_profile.entity.AccountProfile;
import com.example.attack_on_monday_backend.account_profile.repository.AccountProfileRepository;
import com.example.attack_on_monday_backend.agile_board.entity.AgileBoard;
import com.example.attack_on_monday_backend.agile_board.repository.AgileBoardRepository;
import com.example.attack_on_monday_backend.agile_board.service.request.CreateAgileBoardRequest;
import com.example.attack_on_monday_backend.agile_board.service.response.CreateAgileBoardResponse;
import com.example.attack_on_monday_backend.agile_board.service.response.ReadAgileBoardResponse;
import com.example.attack_on_monday_backend.kanban_ticket.entity.KanbanTicket;
import com.example.attack_on_monday_backend.kanban_ticket.repository.KanbanTicketRepository;
import com.example.attack_on_monday_backend.project.entity.Project;
import com.example.attack_on_monday_backend.project.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgileBoardServiceImpl implements AgileBoardService {

    final private AccountRepository accountRepository;
    final private AccountProfileRepository accountProfileRepository;

    final private ProjectRepository projectRepository;
    final private AgileBoardRepository agileBoardRepository;
    final private KanbanTicketRepository kanbanTicketRepository;

    @Override
    public ReadAgileBoardResponse read(Long agileBoardId, Integer page, Integer perPage) {
        Optional<AgileBoard> maybeAgileBoard = agileBoardRepository.findByIdWithWriter(agileBoardId);

        if (maybeAgileBoard.isEmpty()) {
            log.info("정보가 없습니다!");
            return null;
        }

        AgileBoard agileBoard = maybeAgileBoard.get();

        Pageable pageable = PageRequest.of(page - 1, perPage);
        Page<KanbanTicket> paginatedAgileBoard = kanbanTicketRepository.findAllByAgileBoardId(agileBoardId, pageable);

        return ReadAgileBoardResponse.from(agileBoard, paginatedAgileBoard.getContent(),
                paginatedAgileBoard.getTotalElements(), paginatedAgileBoard.getTotalPages());
    }

    @Override
    public CreateAgileBoardResponse register(CreateAgileBoardRequest createAgileBoardRequest) {
        log.info("accountId: {}", createAgileBoardRequest.getAccountId());

        Account account = accountRepository.findById(createAgileBoardRequest.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account 존재하지 않음"));

        log.info("account: {}", account);

        AccountProfile accountProfile = accountProfileRepository.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("AccountProfile not found"));

        log.info("account profile: {}", accountProfile);

        Project project = projectRepository.findById(createAgileBoardRequest.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        log.info("account project: {}", accountProfile);

        AgileBoard savedAgileBoard = agileBoardRepository.save(createAgileBoardRequest.toAgileBoard(accountProfile, project));
        return CreateAgileBoardResponse.from(savedAgileBoard, project.getId());
    }
}
