package com.example.attack_on_monday_backend.project.service.response;

import com.example.attack_on_monday_backend.project.entity.Project;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateProjectResponse {
    private final Long id;
    private final String title;
    private final String writerNickname;
    private final LocalDateTime createDate;

    public static CreateProjectResponse from(Project project) {
        return new CreateProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getWriter().getNickname(),
                project.getCreateDate()
        );
    }
}
