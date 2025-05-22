package com.laoumri.supsharespringbootsocialmediaapp.controllers;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.reset.ResetPasswordRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.MessageResponse;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.reset.FindUserResponse;
import com.laoumri.supsharespringbootsocialmediaapp.enums.code.EUserCode;
import com.laoumri.supsharespringbootsocialmediaapp.nodes.User;
import com.laoumri.supsharespringbootsocialmediaapp.security.enums.ECookie;
import com.laoumri.supsharespringbootsocialmediaapp.security.services.CookiesService;
import com.laoumri.supsharespringbootsocialmediaapp.security.services.JwtTokenService;
import com.laoumri.supsharespringbootsocialmediaapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping(("/api/v1/users"))
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final CookiesService cookiesService;

    @GetMapping("/find-user")
    public ResponseEntity<MessageResponse> findUser(@RequestParam String email) {
        User user = userService.findUserByEmail(email);
        FindUserResponse res = FindUserResponse.builder()
                .email(user.getUsername())
                .profilePhotoUrl(user.getProfile().getProfilePhotoUrl())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(EUserCode.USER_FOUND, Instant.now(), res));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponse> resetPassword(
            @RequestBody ResetPasswordRequest req,
            @CookieValue("RESET_PASSWORD_COOKIE") String token
    ) {
        String email = jwtTokenService.getEmailFromJwtToken(token);
        userService.updatePassword(req, email);

        // Clear Cookie
        ResponseCookie resetCookie = cookiesService.clearCookie(ECookie.RESET_PASSWORD_COOKIE);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, resetCookie.toString())
                .body(new MessageResponse(EUserCode.PASSWORD_UPDATED_SUCCESSFULLY, Instant.now(), null));
    }
}
