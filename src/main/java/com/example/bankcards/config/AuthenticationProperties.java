package com.example.bankcards.config;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Data
@Validated
@ConfigurationProperties("reset-token")
public class AuthenticationProperties {
    @NotNull
    @Positive
    private Integer length;
    @NotNull
    private Duration expire;
}
