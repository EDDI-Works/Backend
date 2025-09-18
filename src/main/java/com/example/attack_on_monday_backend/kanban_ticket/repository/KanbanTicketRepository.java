package com.example.attack_on_monday_backend.kanban_ticket.repository;

import com.example.attack_on_monday_backend.kanban_ticket.entity.KanbanTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KanbanTicketRepository extends JpaRepository<KanbanTicket, Long> {
    @Query("SELECT kt FROM KanbanTicket kt " +
            "JOIN FETCH kt.writer w " +
            "JOIN FETCH kt.agileBoard ab " +
            "WHERE ab.id = :agileBoardId")
    Page<KanbanTicket> findAllByAgileBoardId(Long agileBoardId, Pageable pageable);
}
