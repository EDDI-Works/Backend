package com.example.attack_on_monday_backend.github_authentication.service;

import com.example.attack_on_monday_backend.github_authentication.service.response.GithubLoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class GithubAuthenticationServiceImpl implements GithubAuthenticationService {

    private final String loginUrl;
    private final String clientId;
    private final String redirectUri;

    private final String clientSecret;
    private final String tokenRequestUri;

    private final RestTemplate restTemplate;

    public GithubAuthenticationServiceImpl(
            @Value("${github.login-url}") String loginUrl,
            @Value("${github.client-id}") String clientId,
            @Value("${github.redirect-uri}") String redirectUri,
            @Value("${github.client-secret}") String clientSecret,
            @Value("${github.token-request-uri}") String tokenRequestUri,
            RestTemplate restTemplate) {

        this.loginUrl = loginUrl;
        this.clientId = clientId;
        this.redirectUri = redirectUri;

        this.clientSecret = clientSecret;
        this.tokenRequestUri = tokenRequestUri;

        this.restTemplate = restTemplate;
    }

    @Override
    public String requestOauthLink() {
        String state = UUID.randomUUID().toString();
        String formattedOauthLink = String.format("%s?client_id=%s&redirect_uri=%s&scope=read:user user:email&state=%s",
                loginUrl, clientId, redirectUri, state);

        return formattedOauthLink;
    }

    @Override
    public GithubLoginResponse handleLogin(String code) {
        Map<String, Object> tokenResponse = getAccessToken(code);
        String accessToken = (String) tokenResponse.get("access_token");

        return null;
    }

    private Map<String, Object> getAccessToken(String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("redirect_uri", redirectUri);
        formData.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    tokenRequestUri,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                throw new IllegalStateException("GitHub OAuth 토큰 요청 실패: " + response.getStatusCode());
            }

            return response.getBody();

        } catch (Exception e) {
            log.error("GitHub OAuth 토큰 발급 실패: {}", e.getMessage(), e);
            throw new RuntimeException("GitHub 토큰 발급 중 오류가 발생했습니다.", e);
        }
    }
}
