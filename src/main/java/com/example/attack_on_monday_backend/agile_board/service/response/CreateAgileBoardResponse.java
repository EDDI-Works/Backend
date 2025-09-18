package com.example.attack_on_monday_backend.agile_board.service.response;

import com.example.attack_on_monday_backend.agile_board.entity.AgileBoard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateAgileBoardResponse {
    private final Long id;
    private final String title;
    private final String writerNickname;
    private final LocalDateTime createDate;

    private final Long projectId;

    public static CreateAgileBoardResponse from(AgileBoard agileBoard, Long projectId) {
        return new CreateAgileBoardResponse(
                agileBoard.getId(),
                agileBoard.getTitle(),
                agileBoard.getWriter().getNickname(),
                agileBoard.getCreateDate(),
                projectId
        );
    }
}
