package com.example.attack_on_monday_backend.team_member.repository;

import com.example.attack_on_monday_backend.team_member.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember,Long> {
    
    @Query("SELECT tm FROM TeamMember tm JOIN FETCH tm.team WHERE tm.accountId = :accountId")
    List<TeamMember> findByAccountIdWithTeam(@Param("accountId") Long accountId);
    
    boolean existsByTeamIdAndAccountId(Long teamId, Long accountId);
}
