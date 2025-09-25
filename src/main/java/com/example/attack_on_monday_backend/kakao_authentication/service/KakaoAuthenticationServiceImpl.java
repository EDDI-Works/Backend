package com.example.attack_on_monday_backend.kakao_authentication.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KakaoAuthenticationServiceImpl implements KakaoAuthenticationService {


    private final String loginUrl;
    private final String clientId;
    private final String redirectUri;


    public KakaoAuthenticationServiceImpl(
            @Value("${github.login-url}") String loginUrl,
            @Value("${github.client-id}") String clientId,
            @Value("${github.redirect-uri}") String redirectUri
    ){
        this.loginUrl = loginUrl;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
    }



    @Override
    public String requestKakaoAuthenticationLink() {

        return String.format("%soauth/authorize?client_id=%s&redirect_uri=%s&response_type=code",
                loginUrl, clientId, redirectUri);

    }
}
