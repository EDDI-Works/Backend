package com.example.attack_on_monday_backend.meeting_board.controller.request_form;

import com.example.attack_on_monday_backend.meeting_board.service.model.BoardSnapshot;
import com.example.attack_on_monday_backend.meeting_board.service.request.UpsertBoardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UpsertBoardRequestForm {
    @NotNull
    private final BoardSnapshot snapshot;

    public UpsertBoardRequest toUpsertRequest(Long accountId){
        return new UpsertBoardRequest(accountId, snapshot);
    }
}
