package com.example.attack_on_monday_backend.agile_board.controller.response_form;

import com.example.attack_on_monday_backend.agile_board.service.response.CreateAgileBoardResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateAgileBoardResponseForm {
    private final Long id;
    private final String title;
    private final String writerNickname;
    private final LocalDateTime createDate;

    private final Long projectId;

    public static CreateAgileBoardResponseForm from(CreateAgileBoardResponse response) {
        return new CreateAgileBoardResponseForm(
                response.getId(),
                response.getTitle(),
                response.getWriterNickname(),
                response.getCreateDate(),
                response.getProjectId()
        );
    }
}
