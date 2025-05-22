package com.laoumri.supsharespringbootsocialmediaapp.security.services;

public interface JwtTokenService {
    String generateJwtTokenFromUsername(String email);
    String generateJwtTokenFromUsername(String username, Long expiration);
    String getEmailFromJwtToken(String token);
    boolean isJwtTokenValid(String token);
}
