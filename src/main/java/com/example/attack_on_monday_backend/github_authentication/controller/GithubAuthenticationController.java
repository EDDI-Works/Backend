package com.example.attack_on_monday_backend.github_authentication.controller;

import com.example.attack_on_monday_backend.account.entity.LoginType;
import com.example.attack_on_monday_backend.github_authentication.controller.response_form.GithubLoginResponseForm;
import com.example.attack_on_monday_backend.github_authentication.service.GithubAuthenticationService;
import com.example.attack_on_monday_backend.github_authentication.service.response.GithubLoginResponse;
import com.example.attack_on_monday_backend.redis_cache.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/github-authentication")
@RequiredArgsConstructor
public class GithubAuthenticationController {

    private final GithubAuthenticationService githubAuthenticationService;
    private final RedisCacheService redisCacheService;

    @GetMapping("/request-oauth-link")
    public String githubOauthLink() {
        return githubAuthenticationService.requestOauthLink();
    }

    @GetMapping("/login")
    public GithubLoginResponseForm githubLogin(@RequestParam("code") String code) throws Exception {
        log.info("Github Login Request");

        try {
            GithubLoginResponse loginResponse = githubAuthenticationService.handleLogin(code);
            String redisToken = loginResponse.isNewUser()
                    ? createTemporaryUserToken(loginResponse.getToken())
                    : createUserTokenWithAccessToken(loginResponse.getAccountId(), loginResponse.getToken());

            return GithubLoginResponseForm.from(
                    loginResponse.isNewUser(),
                    redisToken,
                    loginResponse.getNickname(),
                    loginResponse.getEmail(),
                    LoginType.GITHUB
            );
        } catch (Exception e) {
            log.error("Github 로그인 에러", e);
            return null;
        }
    }

    private String createUserTokenWithAccessToken(Long accountId, String accessToken) {
        try {
            String userToken = UUID.randomUUID().toString();
            redisCacheService.setKeyAndValue(accountId, accessToken);
            redisCacheService.setKeyAndValue(userToken, accountId);
            return userToken;
        } catch (Exception e) {
            throw new RuntimeException("Error storing token in Redis: " + e.getMessage());
        }
    }

    private String createTemporaryUserToken(String accessToken) {
        try {
            String userToken = UUID.randomUUID().toString();
            redisCacheService.setKeyAndValue(userToken, accessToken, Duration.ofMinutes(5));
            return userToken;
        } catch (Exception e) {
            throw new RuntimeException("Error storing token in Redis: " + e.getMessage());
        }
    }
}
