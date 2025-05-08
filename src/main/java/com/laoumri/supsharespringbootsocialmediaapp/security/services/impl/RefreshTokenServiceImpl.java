package com.laoumri.supsharespringbootsocialmediaapp.security.services.impl;

import com.laoumri.supsharespringbootsocialmediaapp.entities.RefreshToken;
import com.laoumri.supsharespringbootsocialmediaapp.entities.User;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.RefreshTokenRepository;
import com.laoumri.supsharespringbootsocialmediaapp.security.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Value("${jwt.refreshExpirationMS}")
    private long refreshExpirationMS;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public String createRefreshToken(User user) {
        deleteByUserId(user.getId());
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiresAt(Instant.now().plusMillis(refreshExpirationMS))
                .build();

        return refreshTokenRepository.save(refreshToken).getToken();
    }

    @Override
    public boolean isExpired(RefreshToken token) {
        if(Instant.now().compareTo(token.getExpiresAt()) > 0) {
            refreshTokenRepository.delete(token);
            return true;
        }
        return false;
    }

    @Override
    public void deleteByUserId(UUID userId) {
        refreshTokenRepository.deleteByUser(userId);
    }

}
