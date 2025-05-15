package com.laoumri.supsharespringbootsocialmediaapp.services;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.login.RegisterRequest;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Profile;

public interface ProfileService {
    Profile save(RegisterRequest registerRequest);
}
