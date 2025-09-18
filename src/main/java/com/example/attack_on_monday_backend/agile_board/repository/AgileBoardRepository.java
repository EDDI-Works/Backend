package com.example.attack_on_monday_backend.agile_board.repository;

import com.example.attack_on_monday_backend.agile_board.entity.AgileBoard;
import com.example.attack_on_monday_backend.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AgileBoardRepository extends JpaRepository<AgileBoard, Long> {
    @Query("SELECT ab FROM AgileBoard ab " +
            "JOIN FETCH ab.writer w " +
            "JOIN FETCH ab.project p " +
            "WHERE p.id = :projectId")
    Page<AgileBoard> findAllByProjectId(Long projectId, Pageable pageable);

    @Query("SELECT ab FROM AgileBoard ab JOIN FETCH ab.writer WHERE ab.id = :agileBoardId")
    Optional<AgileBoard> findByIdWithWriter(Long agileBoardId);
}
