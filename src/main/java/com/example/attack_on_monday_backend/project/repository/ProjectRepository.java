package com.example.attack_on_monday_backend.project.repository;

import com.example.attack_on_monday_backend.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p JOIN FETCH p.writer ORDER BY p.id DESC")
    Page<Project> findAllWithWriter(Pageable pageable);
}
