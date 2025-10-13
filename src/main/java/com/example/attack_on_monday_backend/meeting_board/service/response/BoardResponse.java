package com.example.attack_on_monday_backend.meeting_board.service.response;

import com.example.attack_on_monday_backend.meeting_board.service.model.BoardSnapshot;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class BoardResponse {
    private final String publicId;
    private final BoardSnapshot boardSnapshot;
    private final LocalDateTime updatedAt;

    public static BoardResponse from(String publicId, BoardSnapshot boardSnapshot, LocalDateTime updatedAt) {
        return new BoardResponse(publicId, boardSnapshot, updatedAt);
    }
}
