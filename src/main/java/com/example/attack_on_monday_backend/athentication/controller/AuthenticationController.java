package com.example.attack_on_monday_backend.athentication.controller;

import com.example.attack_on_monday_backend.athentication.service.AuthenticationService;
import com.example.attack_on_monday_backend.redis_cache.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication")
public class AuthenticationController {

    private final RedisCacheService redisCacheService;
    private final AuthenticationService authenticationService;

    @GetMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestHeader("AuthenticationHeader") String authenticationHeader) {

        String userToken = authenticationHeader.replace("Bearer ", "").trim();
        Long accountId = redisCacheService.getValueByKey(userToken, Long.class);

        boolean authenticateResult = authenticationService.authenticate(userToken, accountId);

        if(authenticateResult){
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.badRequest().build();
        }


    }


}
