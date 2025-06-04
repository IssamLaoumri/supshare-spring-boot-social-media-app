package com.laoumri.supsharespringbootsocialmediaapp.services.impl;

import com.laoumri.supsharespringbootsocialmediaapp.enums.code.EUserCode;
import com.laoumri.supsharespringbootsocialmediaapp.exceptions.global.NotFoundException;
import com.laoumri.supsharespringbootsocialmediaapp.nodes.PasswordHistory;
import com.laoumri.supsharespringbootsocialmediaapp.nodes.User;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.neo4j.PasswordHistoryRepository;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.neo4j.UserRepository;
import com.laoumri.supsharespringbootsocialmediaapp.services.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {
    private final UserRepository userRepository;
    private final PasswordHistoryRepository passwordHistoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean isPasswordReused(String email, String newRawPassword) {
        List<PasswordHistory> historyList = passwordHistoryRepository.findTop5ByUserEmail(email);
        for (PasswordHistory history : historyList) {
            if (passwordEncoder.matches(newRawPassword, history.getPasswordHash())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void updatePassword(String email, String newRawPassword) {
        User user = userRepository.findByUsername(email)
                .orElseThrow(() -> new NotFoundException(EUserCode.USER_NOT_FOUND, "User not found."));

        // Store current password hash in history
        PasswordHistory history = new PasswordHistory();
        history.setPasswordHash(user.getPassword());
        history.setCreatedAt(Instant.now());
        history.setBelongsTo(user);

        user.getPasswordHistories().add(history);

        // Hash and update password
        String newHash = passwordEncoder.encode(newRawPassword);
        user.setPassword(newHash);

        // Save both User and PasswordHistory
        userRepository.save(user);

        // Clean up old history
        deleteOldHistories(email);
    }

    @Override
    public void deleteOldHistories(String email) {
        List<PasswordHistory> toDelete = passwordHistoryRepository.findOldPasswordHistories(email);
        List<UUID> ids = toDelete.stream().map(PasswordHistory::getId).toList();
        if (!ids.isEmpty()) {
            passwordHistoryRepository.deleteByIds(ids);
        }
    }
}
