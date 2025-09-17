package com.example.attack_on_monday_backend.github_authentication.controller;

import com.example.attack_on_monday_backend.github_authentication.controller.response_form.GithubLoginResponseForm;
import com.example.attack_on_monday_backend.github_authentication.service.GithubAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/login")
    public GithubLoginResponseForm githubLogin(@RequestParam("code") String code) throws Exception {
        log.info("Github Login Request");

        try {
            githubAuthenticationService.handleLogin(code);
            return null;
        } catch (Exception e) {
            log.error("Github 로그인 에러", e);
            return null;
        }
    }
}
