package com.laoumri.supsharespringbootsocialmediaapp.security.services;

import com.laoumri.supsharespringbootsocialmediaapp.security.enums.ECookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

public interface CookiesService {
    String getValueFromCookie(HttpServletRequest request, ECookie cookieName);
    ResponseCookie generateCookie(ECookie cookieName, String cookieValue);
    ResponseCookie clearCookie(ECookie cookieName);
}
