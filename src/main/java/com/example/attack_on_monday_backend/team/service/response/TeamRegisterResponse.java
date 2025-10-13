package com.example.attack_on_monday_backend.team.service.response;

import com.example.attack_on_monday_backend.team.controller.response_form.TeamRegisterResponseForm;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamRegisterResponse {


    private String teamName;


    public TeamRegisterResponseForm toTeamRegisterResponseForm(){
        return new TeamRegisterResponseForm(teamName);
    }


    public TeamRegisterResponse(String teamName) {
        this.teamName = teamName;
    }
}
