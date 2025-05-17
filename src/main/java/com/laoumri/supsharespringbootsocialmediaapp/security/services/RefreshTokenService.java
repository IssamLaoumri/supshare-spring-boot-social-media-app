package com.laoumri.supsharespringbootsocialmediaapp.security.services;

import com.laoumri.supsharespringbootsocialmediaapp.nodes.RefreshToken;
import com.laoumri.supsharespringbootsocialmediaapp.nodes.User;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {
    Optional<RefreshToken> findByToken(String token);
    String createRefreshToken(User user);
    boolean isExpired(RefreshToken token);
    void deleteByUserId(UUID userId);
}
