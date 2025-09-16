package com.example.attack_on_monday_backend.github_authentication.controller;

import com.example.attack_on_monday_backend.github_authentication.service.GithubAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/github-authentication")
@RequiredArgsConstructor
public class GithubAuthenticationController {

    private final GithubAuthenticationService githubAuthenticationService;

    @GetMapping("/request-oauth-link")
    public String githubOauthLink() {
        return githubAuthenticationService.requestOauthLink();
    }
}
