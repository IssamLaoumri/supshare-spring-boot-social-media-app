package com.laoumri.supsharespringbootsocialmediaapp.controllers;

import com.laoumri.supsharespringbootsocialmediaapp.commons.constants.APIConstants;
import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.LoginRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.RegisterRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.AuthResponse;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.MessageResponse;
import com.laoumri.supsharespringbootsocialmediaapp.entities.RefreshToken;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Role;
import com.laoumri.supsharespringbootsocialmediaapp.entities.User;
import com.laoumri.supsharespringbootsocialmediaapp.enums.code.EAuthCode;
import com.laoumri.supsharespringbootsocialmediaapp.enums.code.ETokenCode;
import com.laoumri.supsharespringbootsocialmediaapp.exceptions.global.BadRequestException;
import com.laoumri.supsharespringbootsocialmediaapp.exceptions.global.NotFoundException;
import com.laoumri.supsharespringbootsocialmediaapp.security.enums.ECookie;
import com.laoumri.supsharespringbootsocialmediaapp.security.services.CookiesService;
import com.laoumri.supsharespringbootsocialmediaapp.security.services.JwtTokenService;
import com.laoumri.supsharespringbootsocialmediaapp.security.services.RefreshTokenService;
import com.laoumri.supsharespringbootsocialmediaapp.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequestMapping(APIConstants.AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final CookiesService cookiesService;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest registerRequest) {
        // Create a new User
        User user = authService.register(registerRequest);

        // Auto login after successful signup
        return login(new LoginRequest(user.getUsername(), registerRequest.getPassword()));
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponse> login(@RequestBody LoginRequest loginRequest) {
        // Authenticate the user
        User user = authService.login(loginRequest);

        String accessToken = jwtTokenService.generateJwtTokenFromUsername(loginRequest.getEmail());
        String refreshToken = refreshTokenService.createRefreshToken(user);

        // Generate access and refresh Cookies with their value
        ResponseCookie accessTokenCookie = cookiesService.generateCookie(ECookie.ACCESS_TOKEN_COOKIE, accessToken);
        ResponseCookie refreshTokenCookie = cookiesService.generateCookie(ECookie.REFRESH_TOKEN_COOKIE, refreshToken);

        // Setup Cookies into headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        httpHeaders.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        // Create new Authentication response
        AuthResponse authResponse = AuthResponse.builder()
                .id(user.getId())
                .firstname(user.getProfile().getFirstname())
                .lastname(user.getProfile().getLastname())
                .username(user.getProfile().getUsername())
                .email(user.getUsername())
                .bDay(user.getProfile().getBDay())
                .bMonth(user.getProfile().getBMonth())
                .bYear(user.getProfile().getBYear())
                .gender(user.getProfile().getGender().name())
                .roles(user.getRoles()
                        .stream()
                        .map(Role::getAuthority)
                        .collect(Collectors.toSet())
                )
                .build();

        // Create and return message OK
        MessageResponse res = new MessageResponse(EAuthCode.LOGIN_SUCCESS, Instant.now(), authResponse);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(res);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<MessageResponse> refreshToken(HttpServletRequest request){
        // TODO: Encapsulate this api logic into service findByToken
        String refreshToken = cookiesService.getValueFromCookie(request, ECookie.REFRESH_TOKEN_COOKIE);
        if ((refreshToken != null) && (!refreshToken.isEmpty())) {
            return refreshTokenService.findByToken(refreshToken)
                    .filter(token -> !refreshTokenService.isExpired(token))
                    .map(RefreshToken::getUser)
                    .map(user -> {
                        String accessToken = jwtTokenService.generateJwtTokenFromUsername(user.getUsername());
                        ResponseCookie jwtCookie = cookiesService.generateCookie(ECookie.ACCESS_TOKEN_COOKIE, accessToken);

                        return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                                .body(new MessageResponse(ETokenCode.REFRESH_TOKEN_SUCCESS, Instant.now(), "Token refreshed successfully."));
                    })
                    .orElseThrow(() -> new NotFoundException(ETokenCode.REFRESH_TOKEN_NOT_FOUND,"Refresh token is not in database!"));
        }

        throw new BadRequestException(ETokenCode.REFRESH_TOKEN_EMPTY, "Refresh Token is empty!");
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MessageResponse> logout(@AuthenticationPrincipal User userDetails){
        // Delete user's refresh token
        refreshTokenService.deleteByUserId(userDetails.getId());

        // Clear Cookies
        ResponseCookie jwtCookie = cookiesService.clearCookie(ECookie.ACCESS_TOKEN_COOKIE);
        ResponseCookie jwtRefreshCookie = cookiesService.clearCookie(ECookie.REFRESH_TOKEN_COOKIE);

        // Build a new Message Response
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new MessageResponse(EAuthCode.SIGNED_OUT_SUCCESS, Instant.now(), "You've been signed out!"));
    }
}
