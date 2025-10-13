package com.example.attack_on_monday_backend.account.service;

import com.example.attack_on_monday_backend.account.entity.Account;
import com.example.attack_on_monday_backend.account.entity.AccountRoleType;
import com.example.attack_on_monday_backend.account.entity.LoginType;
import com.example.attack_on_monday_backend.account.entity.RoleType;
import com.example.attack_on_monday_backend.account.repository.AccountRepository;
import com.example.attack_on_monday_backend.account.repository.AccountRoleTypeRepository;
import com.example.attack_on_monday_backend.account.service.request.RegisterNormalAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    final private AccountRepository accountRepository;
    final private AccountRoleTypeRepository accountRoleTypeRepository;

    @Override
    public Account createAccount(RegisterNormalAccountRequest request) {
        AccountRoleType accountRoleType = accountRoleTypeRepository.findByRoleType(RoleType.USER)
                .orElseThrow(() -> new IllegalStateException("RoleType.USER 이 DB에 없습니다."));

        Account account = new Account(accountRoleType);
        return accountRepository.save(account);
    }

    @Override
    public boolean authenticateAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("회원 인증 중 회원을 찾을 수 없습니다"));
        if (account == null) {
            return false;
        } else{
            return true;
        }
    }
}
