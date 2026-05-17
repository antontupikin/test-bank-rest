package com.example.bankcards.crypto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("crypto")
@Data
@Validated
public class CryptoProperties {
    @NotBlank
    private String password;
}
