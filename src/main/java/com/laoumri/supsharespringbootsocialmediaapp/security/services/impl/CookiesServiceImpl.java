package com.laoumri.supsharespringbootsocialmediaapp.security.services.impl;

import com.laoumri.supsharespringbootsocialmediaapp.security.enums.ECookie;
import com.laoumri.supsharespringbootsocialmediaapp.security.services.CookiesService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

@Service
public class CookiesServiceImpl implements CookiesService {
    @Override
    public String getValueFromCookie(HttpServletRequest request, ECookie cookieName) {
        Cookie cookie = WebUtils.getCookie(request, cookieName.name());
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    @Override
    public ResponseCookie generateCookie(ECookie cookieName, String cookieValue) {
        return ResponseCookie
                .from(cookieName.name(), cookieValue)
                .path("/api")
                .maxAge((long)24*60*60)
                .httpOnly(true)
                .secure(true)
                .sameSite("none")
                .build();
    }

    @Override
    public ResponseCookie clearCookie(ECookie cookieName) {
        return ResponseCookie.from(cookieName.name(), "").path("/api").maxAge(0).build();
    }
}
