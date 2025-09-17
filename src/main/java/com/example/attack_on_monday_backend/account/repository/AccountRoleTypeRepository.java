package com.example.attack_on_monday_backend.account.repository;

import com.example.attack_on_monday_backend.account.entity.AccountRoleType;
import com.example.attack_on_monday_backend.account.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRoleTypeRepository extends JpaRepository<AccountRoleType, Long> {
    Optional<AccountRoleType> findByRoleType(RoleType roleType);
}
