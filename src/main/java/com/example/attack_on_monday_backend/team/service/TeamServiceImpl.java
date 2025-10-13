package com.example.attack_on_monday_backend.team.service;


import com.example.attack_on_monday_backend.project.entity.Project;
import com.example.attack_on_monday_backend.project.repository.ProjectRepository;
import com.example.attack_on_monday_backend.team.entity.Team;
import com.example.attack_on_monday_backend.team.repository.TeamRepository;
import com.example.attack_on_monday_backend.team.service.request.TeamRegisterRequest;
import com.example.attack_on_monday_backend.team.service.response.TeamListResponse;
import com.example.attack_on_monday_backend.team.service.response.TeamRegisterResponse;
import com.example.attack_on_monday_backend.team_member.entity.TeamMember;
import com.example.attack_on_monday_backend.team_member.repository.TeamMemberRepository;
import com.example.attack_on_monday_backend.team_member.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamMemberService teamMemberService;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public TeamRegisterResponse teamRegister(TeamRegisterRequest teamRegisterRequest) {

        Team team = Team.builder()
                .name(teamRegisterRequest.getName())
                .build();

        // Team을 먼저 저장
        Team savedTeam = teamRepository.save(team);

        // 저장된 Team으로 TeamMember 생성
        teamMemberService.teamMemberAddAdmin(savedTeam, teamRegisterRequest.getAccountId());


        return new TeamRegisterResponse(savedTeam.getName());
    }

    @Override
    public TeamListResponse getTeamsByAccountId(Long accountId) {
        List<TeamMember> teamMembers = teamMemberRepository.findByAccountIdWithTeam(accountId);
        
        List<TeamListResponse.TeamInfo> teams = teamMembers.stream()
            .map(teamMember -> {
                Team team = teamMember.getTeam();
                // 팀 ID로 프로젝트 조회
                List<Project> projects = projectRepository.findByTeamIdWithWriter(team.getId());
                
                List<TeamListResponse.ProjectInfo> projectInfos = projects.stream()
                    .map(project -> new TeamListResponse.ProjectInfo(
                        project.getId(),
                        project.getTitle()
                    ))
                    .collect(Collectors.toList());
                
                return new TeamListResponse.TeamInfo(
                    team.getId(),
                    team.getName(),
                    projectInfos
                );
            })
            .collect(Collectors.toList());
        
        return new TeamListResponse(teams);
    }

    @Override
    public void validateTeamMember(Long teamId, Long accountId) {
        boolean isMember = teamMemberRepository.existsByTeamIdAndAccountId(teamId, accountId);
        if (!isMember) {
            throw new IllegalArgumentException("해당 팀의 멤버가 아닙니다.");
        }
    }

    @Override
    public int getTeamCountByAccountId(Long accountId) {
        List<TeamMember> teamMembers = teamMemberRepository.findByAccountIdWithTeam(accountId);
        return teamMembers.size();
    }
}
