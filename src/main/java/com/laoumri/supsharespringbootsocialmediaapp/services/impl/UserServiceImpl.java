package com.laoumri.supsharespringbootsocialmediaapp.services.impl;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.login.RegisterRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.reset.ResetPasswordRequest;
import com.laoumri.supsharespringbootsocialmediaapp.enums.code.EGlobal;
import com.laoumri.supsharespringbootsocialmediaapp.nodes.Profile;
import com.laoumri.supsharespringbootsocialmediaapp.nodes.Role;
import com.laoumri.supsharespringbootsocialmediaapp.nodes.User;
import com.laoumri.supsharespringbootsocialmediaapp.enums.code.EUserCode;
import com.laoumri.supsharespringbootsocialmediaapp.exceptions.global.BadRequestException;
import com.laoumri.supsharespringbootsocialmediaapp.exceptions.global.NotFoundException;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.UserRepository;
import com.laoumri.supsharespringbootsocialmediaapp.services.PasswordService;
import com.laoumri.supsharespringbootsocialmediaapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordService passwordService;

    @Override
    public User save(RegisterRequest registerRequest, Profile profile, Set<Role> roles) {
        if(userRepository.existsByUsername(registerRequest.getEmail()))
            throw new BadRequestException(EUserCode.USER_EMAIL_ADDRESS_ALREADY_EXISTS, "Account with email "+registerRequest.getEmail()+" already exists.");

        User newUser = User.builder()
                .username(registerRequest.getEmail())
                .profile(profile)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(roles)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
        return userRepository.save(newUser);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByUsername(email).orElseThrow(()->
                new NotFoundException(EUserCode.USER_NOT_FOUND,"User not found with email: "+ email)
        );
    }

    @Override
    public void updatePassword(ResetPasswordRequest request, String email) {
        // Check passwords match
        if(!request.getPassword().equals(request.getConfirmPassword()))
            throw new BadRequestException(EGlobal.BAD_REQUEST, "Passwords does not match");

        // Check if the password is reused
        if (passwordService.isPasswordReused(email, request.getPassword())) {
            throw new BadRequestException(EGlobal.BAD_REQUEST, "You’ve already used this password. Please choose a new one.");
        }

        // Otherwise, update the password
        passwordService.updatePassword(email, request.getPassword());
    }
}
