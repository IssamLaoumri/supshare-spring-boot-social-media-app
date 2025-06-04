package com.laoumri.supsharespringbootsocialmediaapp.services.impl;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.login.RegisterRequest;
import com.laoumri.supsharespringbootsocialmediaapp.nodes.Profile;
import com.laoumri.supsharespringbootsocialmediaapp.enums.EGender;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.neo4j.ProfileRepository;
import com.laoumri.supsharespringbootsocialmediaapp.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private static final String defaultProfilePhotoUrl =
            "https://res.cloudinary.com/dlzn1dgwj/image/upload/v1747765472/avatar-1_pqyo20.png";

    @Override
    public Profile save(RegisterRequest registerRequest) {
        Profile newProfile = Profile.builder()
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .username(this.generateUniqueUsername(registerRequest.getFirstname(), registerRequest.getLastname()))
                .bDay(registerRequest.getBDay())
                .bMonth(registerRequest.getBMonth())
                .bYear(registerRequest.getBYear())
                .gender(EGender.valueOf(registerRequest.getGender()))
                .profilePhotoUrl(defaultProfilePhotoUrl)
                .build();
        return profileRepository.save(newProfile);
    }

    private String generateUniqueUsername(String firstname, String lastname) {
        boolean a;
        String username = (firstname+"."+lastname).toLowerCase();
        do{
            boolean check = profileRepository.existsByUsername(username);
            if(check){
                username += String.valueOf(System.currentTimeMillis() * Math.random()).substring(0, 1);
                a = true;
            } else {
                a = false;
            }
        }while(a);
        return username;
    }
}
