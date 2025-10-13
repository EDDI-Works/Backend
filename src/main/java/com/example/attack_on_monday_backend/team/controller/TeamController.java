package com.example.attack_on_monday_backend.team.controller;

import com.example.attack_on_monday_backend.project.service.ProjectService;
import com.example.attack_on_monday_backend.project.service.request.CreateProjectRequest;
import com.example.attack_on_monday_backend.project.service.response.CreateProjectResponse;
import com.example.attack_on_monday_backend.redis_cache.service.RedisCacheService;
import com.example.attack_on_monday_backend.team.controller.request_form.TeamProjectRegisterRequestForm;
import com.example.attack_on_monday_backend.team.controller.request_form.TeamRegisterRequestForm;
import com.example.attack_on_monday_backend.team.controller.response_form.TeamListResponseForm;
import com.example.attack_on_monday_backend.team.controller.response_form.TeamProjectRegisterResponseForm;
import com.example.attack_on_monday_backend.team.controller.response_form.TeamRegisterResponseForm;
import com.example.attack_on_monday_backend.team.service.TeamService;
import com.example.attack_on_monday_backend.team.service.request.TeamRegisterRequest;
import com.example.attack_on_monday_backend.team.service.response.TeamListResponse;
import com.example.attack_on_monday_backend.team.service.response.TeamRegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class TeamController {

    private final RedisCacheService redisCacheService;
    private final TeamService teamService;
    private final ProjectService projectService;


    @PostMapping("/register")
    public ResponseEntity<TeamRegisterResponseForm> register(
            @RequestHeader("Authorization") String token,
            @RequestBody TeamRegisterRequestForm teamRegisterRequestForm
    ){
        String userToken = token.replace("Bearer ", "").trim();
        Long accountId = redisCacheService.getValueByKey(userToken, Long.class);

        if (accountId == null) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // 팀 개수 확인 (최대 2개)
        int teamCount = teamService.getTeamCountByAccountId(accountId);
        if (teamCount >= 2) {
            throw new IllegalArgumentException("팀은 최대 2개까지만 생성할 수 있습니다.");
        }

        TeamRegisterResponse teamRegisterResponse = teamService.teamRegister(
                TeamRegisterRequest.builder()
                        .name(teamRegisterRequestForm.getName())
                        .accountId(accountId)
                        .build()
        );


        return ResponseEntity.ok(teamRegisterResponse.toTeamRegisterResponseForm());
    }

    @GetMapping("/list")
    public ResponseEntity<TeamListResponseForm> getTeamList(
            @RequestHeader("Authorization") String token
    ){
        String userToken = token.replace("Bearer ", "").trim();
        Long accountId = redisCacheService.getValueByKey(userToken, Long.class);

        if (accountId == null) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        TeamListResponse teamListResponse = teamService.getTeamsByAccountId(accountId);

        return ResponseEntity.ok(teamListResponse.toResponseForm());
    }

    @PostMapping("/{teamId}/project/register")
    public ResponseEntity<TeamProjectRegisterResponseForm> registerTeamProject(
            @RequestHeader("Authorization") String token,
            @PathVariable("teamId") Long teamId,
            @RequestBody TeamProjectRegisterRequestForm requestForm
    ){
        String userToken = token.replace("Bearer ", "").trim();
        Long accountId = redisCacheService.getValueByKey(userToken, Long.class);

        if (accountId == null) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // 팀 소속 확인
        teamService.validateTeamMember(teamId, accountId);

        // 프로젝트 생성
        CreateProjectResponse projectResponse = projectService.register(
                CreateProjectRequest.builder()
                        .title(requestForm.getTitle())
                        .teamId(teamId)
                        .accountId(accountId)
                        .build()
        );

        return ResponseEntity.ok(TeamProjectRegisterResponseForm.from(projectResponse));
    }

}
