package com.example.attack_on_monday_backend.account.service.request;

import com.example.attack_on_monday_backend.account.entity.LoginType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterNormalAccountRequest {
    private final LoginType loginType;
}
