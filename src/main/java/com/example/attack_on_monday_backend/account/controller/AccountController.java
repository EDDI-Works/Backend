package com.example.attack_on_monday_backend.account.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Account", description = "계정 관련 API")
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Operation(summary = "회원 가입", description = "이메일/비밀번호로 신규 계정 생성.")
    @PostMapping
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest req) {
        // 데모 응답
        return ResponseEntity.status(201).body(new SignUpResponse(1L, req.email()));
    }

    @Operation(summary = "회원 조회", description = "ID로 회원 단건 조회.")
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(new AccountDto(id, "demo@example.com"));
    }

    // --- DTOs ---
    public record SignUpRequest(@Email @NotBlank String email, @NotBlank String password) {}
    public record SignUpResponse(Long id, String email) {}
    public record AccountDto(Long id, String email) {}
}
