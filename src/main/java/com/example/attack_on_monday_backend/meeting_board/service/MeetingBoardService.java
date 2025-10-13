package com.example.attack_on_monday_backend.meeting_board.service;

import com.example.attack_on_monday_backend.meeting_board.service.request.UpsertBoardRequest;
import com.example.attack_on_monday_backend.meeting_board.service.response.BoardResponse;

public interface MeetingBoardService {
    BoardResponse upsertByPublicId(String publicId, UpsertBoardRequest request);
    BoardResponse read(Long accountId, String publicId);
    void clear(Long accountId, String publicId);
}
