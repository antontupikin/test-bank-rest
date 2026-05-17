package com.example.bankcards.controller.request;

import com.example.bankcards.entity.OperationType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletUpdateBalanceRequest(@NotNull UUID walletId, 
                                         @NotNull OperationType operationType, 
                                         @NotNull
                                         @DecimalMin(value = "0.01")
                                         @Digits(integer = 15, fraction = 2)
                                         BigDecimal amount) {
}
