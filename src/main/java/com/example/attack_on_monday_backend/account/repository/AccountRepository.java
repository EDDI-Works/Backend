package com.example.attack_on_monday_backend.account.repository;

import com.example.attack_on_monday_backend.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
