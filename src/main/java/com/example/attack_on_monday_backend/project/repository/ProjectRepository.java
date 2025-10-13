package com.example.attack_on_monday_backend.project.repository;

import com.example.attack_on_monday_backend.account_profile.entity.AccountProfile;
import com.example.attack_on_monday_backend.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p JOIN FETCH p.writer ORDER BY p.id DESC")
    Page<Project> findAllWithWriter(Pageable pageable);

    @Query("SELECT p FROM Project p JOIN FETCH p.writer WHERE p.id = :projectId")
    Optional<Project> findByIdWithWriter(Long projectId);

    @Query("SELECT p FROM Project p JOIN FETCH p.writer WHERE p.teamId = :teamId")
    List<Project> findByTeamIdWithWriter(@Param("teamId") Long teamId);
    Optional<Project> findByWriterAndTitle(AccountProfile writer, String title);
}
