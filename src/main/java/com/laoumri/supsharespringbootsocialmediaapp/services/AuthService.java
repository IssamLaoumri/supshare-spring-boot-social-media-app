package com.laoumri.supsharespringbootsocialmediaapp.services;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.login.LoginRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.login.RegisterRequest;
import com.laoumri.supsharespringbootsocialmediaapp.nodes.User;

public interface AuthService {
    User register(RegisterRequest registerRequest);
    User login(LoginRequest loginRequest);
    void verifyEmail(String email);
}
