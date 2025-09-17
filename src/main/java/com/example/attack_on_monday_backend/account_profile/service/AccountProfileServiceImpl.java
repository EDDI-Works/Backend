package com.example.attack_on_monday_backend.account_profile.service;

import com.example.attack_on_monday_backend.account.entity.Account;
import com.example.attack_on_monday_backend.account_profile.entity.AccountProfile;
import com.example.attack_on_monday_backend.account_profile.entity.Email;
import com.example.attack_on_monday_backend.account_profile.entity.Nickname;
import com.example.attack_on_monday_backend.account_profile.repository.AccountProfileRepository;
import com.example.attack_on_monday_backend.account_profile.service.request.RegisterAccountProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountProfileServiceImpl implements AccountProfileService {
    final private AccountProfileRepository accountProfileRepository;

    @Override
    public AccountProfile createAccountProfile(Account account, RegisterAccountProfileRequest request) {

        Email email = new Email(request.getEmail());
        Nickname nickname = new Nickname(request.getNickname());

        if (accountProfileRepository.existsByEmail(email.getValue())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if (accountProfileRepository.existsByNickname(nickname.getValue())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        AccountProfile accountProfile = new AccountProfile(account, nickname.getValue(), email.getValue());
        return accountProfileRepository.save(accountProfile);
    }
}
