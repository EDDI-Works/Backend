package com.example.attack_on_monday_backend.project.controller;

import com.example.attack_on_monday_backend.project.controller.request_form.ListProjectRequestForm;
import com.example.attack_on_monday_backend.project.controller.response_form.ListProjectResponseForm;
import com.example.attack_on_monday_backend.project.service.ProjectService;
import com.example.attack_on_monday_backend.project.service.response.ListProjectResponse;
import com.example.attack_on_monday_backend.redis_cache.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
    final private ProjectService projectService;
    final private RedisCacheService redisCacheService;

    @GetMapping("/list")
    public ListProjectResponseForm projectList(
            @RequestHeader("Authorization") String authorizationHeader,
            @ModelAttribute ListProjectRequestForm requestForm
    ) {
        log.info("projectList() -> {}", requestForm);

        String userToken = authorizationHeader.replace("Bearer ", "").trim();
        log.info("인증 토큰: {}", userToken);

        Long accountId = redisCacheService.getValueByKey(userToken, Long.class);
        if (accountId == null) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        ListProjectResponse response = projectService.list(requestForm.toListProjectRequest());

        return ListProjectResponseForm.from(
                List.of(response),
                response.getTotalItems(),
                response.getTotalPages()
        );
    }
}
