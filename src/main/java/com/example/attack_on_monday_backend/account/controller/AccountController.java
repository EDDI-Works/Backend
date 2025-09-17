package com.example.attack_on_monday_backend.account.controller;

import com.example.attack_on_monday_backend.account.controller.request_form.RegisterNormalAccountRequestForm;
import com.example.attack_on_monday_backend.account.entity.Account;
import com.example.attack_on_monday_backend.account.service.AccountService;
import com.example.attack_on_monday_backend.redis_cache.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final RedisCacheService redisCacheService;

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

        return null;
    }
}
