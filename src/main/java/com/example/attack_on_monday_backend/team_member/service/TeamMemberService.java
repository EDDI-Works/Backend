package com.example.attack_on_monday_backend.team_member.service;

import com.example.attack_on_monday_backend.team.entity.Team;

public interface TeamMemberService {

    void teamMemberAddAdmin(Team team, Long accountId);

}
