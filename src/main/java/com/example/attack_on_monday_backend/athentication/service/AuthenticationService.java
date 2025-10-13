package com.example.attack_on_monday_backend.athentication.service;

public interface AuthenticationService {

    boolean authenticate(String token, Long accountId);

}
