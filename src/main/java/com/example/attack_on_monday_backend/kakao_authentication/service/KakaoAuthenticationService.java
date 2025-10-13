package com.example.attack_on_monday_backend.kakao_authentication.service;

import com.example.attack_on_monday_backend.kakao_authentication.service.response.KakaoLoginResponse;

import java.util.Map;

public interface KakaoAuthenticationService {
    String requestKakaoAuthenticationLink();
    Map<String, Object> getAccessToken(String code);
    Map<String, Object> getUserInfo(String accessToken);
    KakaoLoginResponse handleLogin(String code);
    String extractNickname(Map<String, Object> userInfo);
    String extractEmail(Map<String, Object> userInfo);

}
