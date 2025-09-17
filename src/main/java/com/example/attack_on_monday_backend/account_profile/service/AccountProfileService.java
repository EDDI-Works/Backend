package com.example.attack_on_monday_backend.account_profile.service;

import com.example.attack_on_monday_backend.account.entity.Account;
import com.example.attack_on_monday_backend.account_profile.entity.AccountProfile;
import com.example.attack_on_monday_backend.account_profile.service.request.RegisterAccountProfileRequest;

public interface AccountProfileService {
    AccountProfile createAccountProfile(Account account, RegisterAccountProfileRequest request);
}
