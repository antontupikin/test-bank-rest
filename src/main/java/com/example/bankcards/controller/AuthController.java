package com.example.bankcards.controller;

import com.example.bankcards.config.AuthenticationService;
import com.example.bankcards.controller.request.AuthenticationRequest;
import com.example.bankcards.controller.request.TokenRefreshRequest;
import com.example.bankcards.controller.response.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/")
public class AuthController {

    private final AuthenticationService service;

    @Operation(
            summary = "Аутентификация пользователя",
            description = "Этот метод позволяет пользователю аутентифицироваться в системе, предоставив свои учетные данные " +
                    "(логин и пароль). После успешной аутентификации, метод возвращает объект `AuthenticationResponse`, содержащий токен доступа.")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse authenticate(@Valid @RequestBody AuthenticationRequest request) {
        return service.authenticate(request);
    }

    @Operation(
            summary = "Обновление токена",
            description = "Этот метод позволяет пользователю обновить свой токен доступа, предоставив токен обновления. " +
                    "После успешного обновления, метод возвращает объект `AuthenticationResponse`, содержащий новый токен доступа.")
    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse refresh(@Valid @RequestBody TokenRefreshRequest request) {
        return service.refresh(request);
    }

}

