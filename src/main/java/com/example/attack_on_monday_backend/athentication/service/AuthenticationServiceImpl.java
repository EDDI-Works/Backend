package com.example.attack_on_monday_backend.athentication.service;

import com.example.attack_on_monday_backend.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AccountService accountService;


    @Override
    public boolean authenticate(String token, Long accountId) {
        if(accountId == null){
            return false;
        }

        return accountService.authenticateAccount(accountId);
    }
}
