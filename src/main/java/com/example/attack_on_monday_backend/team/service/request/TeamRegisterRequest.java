package com.example.attack_on_monday_backend.team.service.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamRegisterRequest {

    private String name;
    private Long accountId;

}
