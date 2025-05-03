package com.laoumri.supsharespringbootsocialmediaapp.services;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.LoginRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.RegisterRequest;
import com.laoumri.supsharespringbootsocialmediaapp.entities.User;

public interface AuthService {
    User register(RegisterRequest registerRequest);
    User login(LoginRequest loginRequest);
}
