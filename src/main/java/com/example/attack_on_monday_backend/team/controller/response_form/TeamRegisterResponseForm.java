package com.example.attack_on_monday_backend.team.controller.response_form;

import lombok.Getter;

@Getter
public class TeamRegisterResponseForm {

    private String teamName;

    public TeamRegisterResponseForm(String teamName) {
        this.teamName = teamName;
    }

    public TeamRegisterResponseForm() {
    }
}
