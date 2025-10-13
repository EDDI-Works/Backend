package com.example.attack_on_monday_backend.team_member.service;

import com.example.attack_on_monday_backend.team.entity.Team;
import com.example.attack_on_monday_backend.team_member.entity.Permission;
import com.example.attack_on_monday_backend.team_member.entity.TeamMember;
import com.example.attack_on_monday_backend.team_member.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamMemberServiceImpl implements TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;

    @Override
    public void teamMemberAddAdmin(Team team, Long accountId) {

        TeamMember teamMember = TeamMember.builder()
                .team(team)
                .accountId(accountId)
                .permission(Permission.ADMIN)
                .build();

        teamMemberRepository.save(teamMember);
    }
}
