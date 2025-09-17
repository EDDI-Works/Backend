package com.example.attack_on_monday_backend.account_profile.repository;

import com.example.attack_on_monday_backend.account_profile.entity.AccountProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountProfileRepository extends JpaRepository<AccountProfile, Long> {
    @Query("SELECT ap FROM AccountProfile ap WHERE ap.email = :email")
    Optional<AccountProfile> findWithAccountByEmail(@Param("email") String email);
}
