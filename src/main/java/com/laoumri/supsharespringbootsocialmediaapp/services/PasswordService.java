package com.laoumri.supsharespringbootsocialmediaapp.services;

public interface PasswordService {
    boolean isPasswordReused(String email, String newRawPassword);
    void updatePassword(String email, String newRawPassword);
    void deleteOldHistories(String email);
}
