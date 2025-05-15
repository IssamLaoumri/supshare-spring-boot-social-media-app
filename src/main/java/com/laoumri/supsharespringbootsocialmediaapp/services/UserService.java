package com.laoumri.supsharespringbootsocialmediaapp.services;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.login.RegisterRequest;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Profile;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Role;
import com.laoumri.supsharespringbootsocialmediaapp.entities.User;

import java.util.Set;

public interface UserService {
    User save(RegisterRequest registerRequest, Profile profile, Set<Role> roles);
    User findUserByEmail(String email);
}
