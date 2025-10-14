package com.example.attack_on_monday_backend.account.controller;

import com.example.attack_on_monday_backend.account.controller.request_form.RegisterNormalAccountRequestForm;
import com.example.attack_on_monday_backend.account.entity.Account;
import com.example.attack_on_monday_backend.account.service.AccountService;
import com.example.attack_on_monday_backend.account_profile.service.AccountProfileService;
import com.example.attack_on_monday_backend.redis_cache.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final RedisCacheService redisCacheService;
    private final AccountProfileService accountProfileService;

    @PostMapping("/register")
    public String register(@RequestHeader("Authorization") String authorizationHeader,
                           @RequestBody RegisterNormalAccountRequestForm requestForm) {
        log.info("회원 가입 요청: requestForm={}", requestForm);

        String temporaryUserToken = authorizationHeader.replace("Bearer ", "").trim();

        String accessToken = redisCacheService.getValueByKey(temporaryUserToken, String.class);
        if (accessToken == null) {
            throw new IllegalArgumentException("만료되었거나 잘못된 임시 토큰입니다.");
        }

        Account account = accountService.createAccount(requestForm.toRegisterNormalAccountRequest());
        accountProfileService.createAccountProfile(account, requestForm.toRegisterAccountProfileRequest());

        String userToken = issueUserToken(account.getId(), accessToken);
        redisCacheService.deleteByKey(temporaryUserToken);

        return userToken;
    }

    private String issueUserToken(Long accountId, String accessToken) {
        String userToken = UUID.randomUUID().toString();
        redisCacheService.setKeyAndValue(accountId, accessToken);
        redisCacheService.setKeyAndValue(userToken, accountId);

        // access:<accessToken> -> accountId (로그인 역조회용)
        redisCacheService.setKeyAndValue("access:" + accessToken, accountId);
        return userToken;
    }

    // 나중에 삭제할거
    @PostMapping("/login")
    public String login(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new IllegalArgumentException("Authorization 헤더가 없습니다.");
        }

        String accessToken = authorizationHeader.replace("Bearer", "").trim();
        if (accessToken.isBlank()) {
            throw new IllegalArgumentException("Authorization 헤더가 비어 있습니다.");
        }

        // 1) 기존 가입자: 역매핑으로 accountId 조회
        Long accountId = redisCacheService.getValueByKey("access:" + accessToken, Long.class);

        if (accountId == null) {
            // [NEW] 2) 소셜 로그인 최초 유입(미가입): 자동 가입 → 곧장 userToken 발급
            log.info("소셜 로그인 자동가입 진행(access:{}). 역매핑 없음 → 계정 생성", accessToken);
            Account account = accountService.createAccount(null); // 최소 계정 생성
            accountId = account.getId();

            // 자동가입 시 매핑 세팅
            redisCacheService.setKeyAndValue(accountId, accessToken);         // accountId -> accessToken
            redisCacheService.setKeyAndValue("access:" + accessToken, accountId); // access:<accessToken> -> accountId
        }

        // 토큰 회전 발급
        String newUserToken = UUID.randomUUID().toString();
        redisCacheService.setKeyAndValue(newUserToken, accountId); // userToken -> accountId

        return newUserToken;
    }

}
