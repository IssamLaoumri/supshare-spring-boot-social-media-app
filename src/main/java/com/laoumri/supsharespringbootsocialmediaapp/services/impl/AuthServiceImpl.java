package com.laoumri.supsharespringbootsocialmediaapp.services.impl;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.login.LoginRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.login.RegisterRequest;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Profile;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Role;
import com.laoumri.supsharespringbootsocialmediaapp.entities.User;
import com.laoumri.supsharespringbootsocialmediaapp.enums.code.ERoleCode;
import com.laoumri.supsharespringbootsocialmediaapp.exceptions.global.NotFoundException;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.RoleRepository;
import com.laoumri.supsharespringbootsocialmediaapp.security.enums.ERole;
import com.laoumri.supsharespringbootsocialmediaapp.services.AuthService;
import com.laoumri.supsharespringbootsocialmediaapp.services.ProfileService;
import com.laoumri.supsharespringbootsocialmediaapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final ProfileService profileService;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public User register(RegisterRequest registerRequest) {
        Set<Role> roles = new HashSet<>();

        if (registerRequest.getRoles() == null) {
            Role defaultRole = roleRepository.findByRoleName(ERole.ROLE_USER)
                    .orElseThrow(() -> new NotFoundException(ERoleCode.ROLE_NOT_FOUND, ERole.ROLE_USER.name() + " role name not found"));
            roles.add(defaultRole);
        } else {
            registerRequest.getRoles().forEach(role -> {
                Role roleEntity = roleRepository.findByRoleName(ERole.valueOf(role))
                        .orElseThrow(() -> new NotFoundException(ERoleCode.ROLE_NOT_FOUND, ERole.ROLE_USER.name() + " role name not found"));
                roles.add(roleEntity);
            });
        }
        Profile profile = profileService.save(registerRequest);
        return userService.save(registerRequest, profile, roles);
    }

    @Override
    public User login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return userService.findUserByEmail(loginRequest.getEmail());
    }
}
