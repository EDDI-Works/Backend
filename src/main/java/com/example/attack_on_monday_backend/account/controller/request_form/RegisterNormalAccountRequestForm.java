package com.example.attack_on_monday_backend.account.controller.request_form;

import com.example.attack_on_monday_backend.account.entity.LoginType;
import com.example.attack_on_monday_backend.account.service.request.RegisterNormalAccountRequest;
import com.example.attack_on_monday_backend.account_profile.service.request.RegisterAccountProfileRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterNormalAccountRequestForm {
    final private String email;
    final private String nickname;
    final private LoginType loginType;

    public RegisterNormalAccountRequest toRegisterNormalAccountRequest() {
        return new RegisterNormalAccountRequest(loginType);
    }

    public RegisterAccountProfileRequest toRegisterAccountProfileRequest() {
        return new RegisterAccountProfileRequest(email, nickname);
    }
}
