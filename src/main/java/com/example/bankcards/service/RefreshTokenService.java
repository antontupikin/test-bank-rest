package com.example.bankcards.service;

import com.example.bankcards.config.SecurityJwtProperties;
import com.example.bankcards.entity.RefreshToken;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.ForbiddenException;
import com.example.bankcards.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final SecurityJwtProperties securityJwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken getByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new ForbiddenException("message.exception.not-found.refresh-token"));
    }

    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);
        refreshToken.setExpiryDate(OffsetDateTime.now().plusNanos(securityJwtProperties.getRefreshExpiration().toNanos()));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(OffsetDateTime.now())) {
            throw new ForbiddenException("message.exception.bad-request.refresh-token.expired");
        }
    }

    public void deleteAllExpired() {
        refreshTokenRepository.deleteAllByExpiryDateBefore(OffsetDateTime.now());
    }
}