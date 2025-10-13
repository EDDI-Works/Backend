package com.example.attack_on_monday_backend.meeting_board.service.request;

import com.example.attack_on_monday_backend.meeting_board.service.model.BoardSnapshot;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class UpsertBoardRequest {
    @NotNull
    private final Long accountId;

    @NotNull
    private final BoardSnapshot boardSnapshot;
}
