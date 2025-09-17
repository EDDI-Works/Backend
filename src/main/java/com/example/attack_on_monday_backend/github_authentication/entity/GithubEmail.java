package com.example.attack_on_monday_backend.github_authentication.entity;

import lombok.Getter;

@Getter
public class GithubEmail {
    private final String value;

    public GithubEmail(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("이메일은 비어있을 수 없습니다.");
        }
        this.value = value;
    }
}
