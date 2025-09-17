package com.example.attack_on_monday_backend.account_profile.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterAccountProfileRequest {
    final private String email;
    final private String nickname;
}
