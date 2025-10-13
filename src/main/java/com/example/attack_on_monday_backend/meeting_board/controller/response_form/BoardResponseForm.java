package com.example.attack_on_monday_backend.meeting_board.controller.response_form;

import com.example.attack_on_monday_backend.meeting_board.service.model.BoardSnapshot;
import com.example.attack_on_monday_backend.meeting_board.service.response.BoardResponse;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class BoardResponseForm {
    private final String publicId;
    private final BoardSnapshot boardSnapshot;
    private final String updatedAt;

    public static BoardResponseForm from(BoardResponse response){
        return new BoardResponseForm(
                response.getPublicId(),
                response.getBoardSnapshot(),
                response.getUpdatedAt() == null ? null : response.getUpdatedAt().toString()
        );
    }
}
