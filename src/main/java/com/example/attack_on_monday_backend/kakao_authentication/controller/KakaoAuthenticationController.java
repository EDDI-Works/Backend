package com.example.attack_on_monday_backend.kakao_authentication.controller;

import com.example.attack_on_monday_backend.kakao_authentication.service.KakaoAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao_authentication")
public class KakaoAuthenticationController {

    private final KakaoAuthenticationService kakaoAuthenticationService;

    @GetMapping("/link")
    public String linkKakaoAuthentication(){
        return kakaoAuthenticationService.requestKakaoAuthenticationLink();
    }
}
