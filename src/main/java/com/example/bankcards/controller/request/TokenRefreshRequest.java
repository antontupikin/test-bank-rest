package com.example.bankcards.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TokenRefreshRequest(
        @NotBlank
        @Size(min = 1, max = 255)
        @JsonProperty("refresh_token")
        String refreshToken) {
}
