package com.example.attack_on_monday_backend.github_authentication.service;

import com.example.attack_on_monday_backend.github_authentication.service.response.GithubLoginResponse;

public interface GithubAuthenticationService {
    String requestOauthLink();
    GithubLoginResponse handleLogin(String code);
}
