package com.example.attack_on_monday_backend.agile_board.repository;

import com.example.attack_on_monday_backend.agile_board.entity.AgileBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AgileBoardRepository extends JpaRepository<AgileBoard, Long> {
    @Query("SELECT ab FROM AgileBoard ab " +
            "JOIN FETCH ab.writer w " +
            "JOIN FETCH ab.project p " +
            "WHERE p.id = :projectId")
    List<AgileBoard> findAllByProjectId(Long projectId);
}
