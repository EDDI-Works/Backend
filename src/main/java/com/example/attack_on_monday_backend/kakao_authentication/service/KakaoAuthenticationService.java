package com.example.attack_on_monday_backend.kakao_authentication.service;

import java.util.Map;

public interface KakaoAuthenticationService {
    String requestKakaoAuthenticationLink();
    Map<String, Object> getAccessToken(String code);
    Map<String, Object> getUserInfo(String accessToken);

}
