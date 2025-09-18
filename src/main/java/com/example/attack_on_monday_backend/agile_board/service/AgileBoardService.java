package com.example.attack_on_monday_backend.agile_board.service;

import com.example.attack_on_monday_backend.agile_board.service.request.CreateAgileBoardRequest;
import com.example.attack_on_monday_backend.agile_board.service.response.CreateAgileBoardResponse;
import com.example.attack_on_monday_backend.agile_board.service.response.ReadAgileBoardResponse;

public interface AgileBoardService {
    ReadAgileBoardResponse read(Long agileBoardId, Integer page, Integer perPage);
    CreateAgileBoardResponse register(CreateAgileBoardRequest createAgileBoardRequest);
}
