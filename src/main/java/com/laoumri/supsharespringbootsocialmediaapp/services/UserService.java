package com.laoumri.supsharespringbootsocialmediaapp.services;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.login.RegisterRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.reset.ResetPasswordRequest;
import com.laoumri.supsharespringbootsocialmediaapp.nodes.Profile;
import com.laoumri.supsharespringbootsocialmediaapp.nodes.Role;
import com.laoumri.supsharespringbootsocialmediaapp.nodes.User;

import java.util.Set;

public interface UserService {
    User save(RegisterRequest registerRequest, Profile profile, Set<Role> roles);
    User findUserByEmail(String email);
    void updatePassword(ResetPasswordRequest resetPasswordRequest, String token);
}
