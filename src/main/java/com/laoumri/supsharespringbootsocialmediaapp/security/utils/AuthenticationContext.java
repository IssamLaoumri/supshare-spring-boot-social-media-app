package com.laoumri.supsharespringbootsocialmediaapp.security.utils;

import com.laoumri.supsharespringbootsocialmediaapp.nodes.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationContext {
    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
