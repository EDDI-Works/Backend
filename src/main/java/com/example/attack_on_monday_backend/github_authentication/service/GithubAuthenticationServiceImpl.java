package com.example.attack_on_monday_backend.github_authentication.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class GithubAuthenticationServiceImpl implements GithubAuthenticationService {

    private final String loginUrl;
    private final String clientId;
    private final String redirectUri;

    public GithubAuthenticationServiceImpl(
            @Value("${github.login-url}") String loginUrl,
            @Value("${github.client-id}") String clientId,
            @Value("${github.redirect-uri}") String redirectUri) {

        this.loginUrl = loginUrl;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
    }

    @Override
    public String requestOauthLink() {
        String state = UUID.randomUUID().toString();
        String formattedOauthLink = String.format("%s?client_id=%s&redirect_uri=%s&scope=read:user user:email&state=%s",
                loginUrl, clientId, redirectUri, state);

        return formattedOauthLink;
    }
}
