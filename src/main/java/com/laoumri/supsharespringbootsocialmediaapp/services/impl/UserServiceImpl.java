package com.laoumri.supsharespringbootsocialmediaapp.services.impl;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.login.RegisterRequest;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Profile;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Role;
import com.laoumri.supsharespringbootsocialmediaapp.entities.User;
import com.laoumri.supsharespringbootsocialmediaapp.enums.code.EUserCode;
import com.laoumri.supsharespringbootsocialmediaapp.exceptions.global.BadRequestException;
import com.laoumri.supsharespringbootsocialmediaapp.exceptions.global.NotFoundException;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.UserRepository;
import com.laoumri.supsharespringbootsocialmediaapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
}
