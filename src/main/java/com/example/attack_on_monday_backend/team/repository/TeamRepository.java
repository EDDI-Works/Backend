package com.example.attack_on_monday_backend.team.repository;

import com.example.attack_on_monday_backend.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
