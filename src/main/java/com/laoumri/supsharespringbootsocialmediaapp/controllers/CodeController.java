package com.laoumri.supsharespringbootsocialmediaapp.controllers;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.reset.SendCodeRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.reset.VerifyCodeRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.MessageResponse;
import com.laoumri.supsharespringbootsocialmediaapp.enums.ECode;
import com.laoumri.supsharespringbootsocialmediaapp.enums.code.ECodeVerif;
import com.laoumri.supsharespringbootsocialmediaapp.nodes.Code;
import com.laoumri.supsharespringbootsocialmediaapp.security.enums.ECookie;
import com.laoumri.supsharespringbootsocialmediaapp.security.services.CookiesService;
import com.laoumri.supsharespringbootsocialmediaapp.security.services.JwtTokenService;
import com.laoumri.supsharespringbootsocialmediaapp.services.CodeService;
import com.laoumri.supsharespringbootsocialmediaapp.services.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/code")
@RequiredArgsConstructor
@Slf4j
public class CodeController {
    private final EmailService emailService;
    private final CodeService codeService;
    private final JwtTokenService jwtTokenService;
    private final CookiesService cookiesService;

    @PostMapping("/sendCode")
    public ResponseEntity<MessageResponse> sendResetPasswordEmail(@Valid @RequestBody SendCodeRequest request) {
        Code code = codeService.save(request.getEmail(), ECode.RESET_PASSWORD);

        String token;
        ResponseCookie cookie = null;

        // Send email to the user with generated password
        Context context = new Context();
        context.setVariable("code", code.getCode());
        context.setVariable("lastname", code.getUser().getProfile().getLastname());
        try {
            emailService.sendEmail(
                    request.getEmail(),
                    "test",
                    "Reset",
                    context
            );

            // Generate a one time use token
            token = jwtTokenService.generateJwtTokenFromUsername(request.getEmail(), 900000L);
            // Build it in a new cookie
            cookie = cookiesService.generateCookie(ECookie.RESET_PASSWORD_COOKIE, token);

            log.info("Email sent successfully");
        } catch (Exception e){
            log.error("Mailing provider encountered a problem. Please Contact the administrator or developers team.", e);
        }

        // Setup Cookies into headers
        HttpHeaders httpHeaders = new HttpHeaders();

        if(cookie != null) {
            httpHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString());
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(new MessageResponse(null, Instant.now(), null));
    }

    @PostMapping("/verifyCode")
    public ResponseEntity<MessageResponse> verifyCode(
            @Valid @RequestBody VerifyCodeRequest request,
            @CookieValue("RESET_PASSWORD_COOKIE") String token
    ) {
        String email = jwtTokenService.getEmailFromJwtToken(token);
        codeService.verify(request.getCode(), email);
        return ResponseEntity.status(HttpStatus.OK).body(
                new MessageResponse(
                        ECodeVerif.CODE_VERIFICATION_SUCCEEDED,
                        Instant.now(),
                        "proceed"
                )
        );
    }
}
