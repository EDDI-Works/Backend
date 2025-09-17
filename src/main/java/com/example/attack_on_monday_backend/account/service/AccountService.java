package com.example.attack_on_monday_backend.account.service;

import com.example.attack_on_monday_backend.account.entity.Account;
import com.example.attack_on_monday_backend.account.service.request.RegisterNormalAccountRequest;

public interface AccountService {
    Account createAccount(RegisterNormalAccountRequest request);
}
