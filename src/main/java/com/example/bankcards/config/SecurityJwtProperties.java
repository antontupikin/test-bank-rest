package com.example.bankcards.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@ConfigurationProperties("security.jwt")
@Data
@Validated
public class SecurityJwtProperties {
    @NotNull
    private Duration expiration;
    @NotNull
    private Duration refreshExpiration;
    @NotBlank
    private String secretKey;
}
