package com.laoumri.supsharespringbootsocialmediaapp.security.services;

public interface JwtTokenService {
    String generateJwtTokenFromUsername(String email);
    String getEmailFromJwtToken(String token);
    boolean isJwtTokenValid(String token);
}
