package com.example.bankcards.controller;

import com.example.bankcards.controller.request.WalletUpdateBalanceRequest;
import com.example.bankcards.controller.response.WalletResponse;
import com.example.bankcards.scenario.wallet.WalletGetScenario;
import com.example.bankcards.scenario.wallet.WalletUpdateScenario;
import com.example.bankcards.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/")
public class WalletController {
    private final UserService userService;
    private final WalletGetScenario walletGetScenario;
    private final WalletUpdateScenario walletUpdateScenario;

    @Operation(
            summary = "Изменение счете",
            description = "Этот метод позволяет пользователю провести операцию по счету" +
                    " сохраняет изменения в системе," +
                    " предоставив необходимые данные в теле запроса. Метод возвращает объект WalletResponse.",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/wallet")
    public WalletResponse change(@Valid @RequestBody WalletUpdateBalanceRequest request, Principal principal) {
        return walletUpdateScenario.updateBalance(request, userService.get(principal).getId());
    }

    @Operation(
            summary = "Получение информации о счете",
            description = "Возвращает информацию о счете по его " +
                    "уникальному идентификатору." +
                    "Метод возвращает объект `WalletResponse`, содержащий информацию о счете.",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/wallet/{id}")
    public WalletResponse findById(@PathVariable UUID id, Principal principal) {
        return walletGetScenario.findByIdAndUser(id, userService.get(principal).getId());
    }
}
