package com.example.attack_on_monday_backend.agile_board.service;

import com.example.attack_on_monday_backend.agile_board.entity.AgileBoard;
import com.example.attack_on_monday_backend.agile_board.repository.AgileBoardRepository;
import com.example.attack_on_monday_backend.agile_board.service.response.ReadAgileBoardResponse;
import com.example.attack_on_monday_backend.kanban_ticket.entity.KanbanTicket;
import com.example.attack_on_monday_backend.kanban_ticket.repository.KanbanTicketRepository;
import com.example.attack_on_monday_backend.project.service.response.ReadProjectResponse;
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
}
