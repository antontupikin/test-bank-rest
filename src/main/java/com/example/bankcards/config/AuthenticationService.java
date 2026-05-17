package com.example.bankcards.config;

import com.example.bankcards.controller.request.AuthenticationRequest;
import com.example.bankcards.controller.request.TokenRefreshRequest;
import com.example.bankcards.controller.response.AuthenticationResponse;
import com.example.bankcards.entity.RefreshToken;
import com.example.bankcards.entity.User;
import com.example.bankcards.service.RefreshTokenService;
import com.example.bankcards.service.UserService;
import com.example.bankcards.util.FieldsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor

public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationProperties authenticationProperties;

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        FieldsUtils.normalizeEmail(request.email()),
                        request.password()
                )
        );
        User user = userService.getByEmail(request.email());
        return generateTokens(user);
    }

    @Transactional
    public AuthenticationResponse refresh(TokenRefreshRequest request) {
        RefreshToken refreshToken = refreshTokenService.getByToken(request.refreshToken());
        refreshTokenService.verifyExpiration(refreshToken);
        return generateTokens(refreshToken.getUser());
    }

    private AuthenticationResponse generateTokens(User user) {
        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        return new AuthenticationResponse(accessToken, refreshToken.getToken(), "Bearer");
    }

    @Transactional
    public void deleteExpiredRefreshToken() {
        refreshTokenService.deleteAllExpired();
    }

}
