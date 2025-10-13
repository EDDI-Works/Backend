package com.example.attack_on_monday_backend.team.service;

import com.example.attack_on_monday_backend.team.controller.request_form.TeamRegisterRequestForm;
import com.example.attack_on_monday_backend.team.service.request.TeamRegisterRequest;
import com.example.attack_on_monday_backend.team.service.response.TeamListResponse;
import com.example.attack_on_monday_backend.team.service.response.TeamRegisterResponse;

public interface TeamService {
    TeamRegisterResponse teamRegister(TeamRegisterRequest teamRegisterRequest);
    TeamListResponse getTeamsByAccountId(Long accountId);
    void validateTeamMember(Long teamId, Long accountId);
    int getTeamCountByAccountId(Long accountId);
}
