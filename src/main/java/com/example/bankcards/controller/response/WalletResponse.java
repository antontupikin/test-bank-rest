package com.example.bankcards.controller.response;

import com.example.bankcards.entity.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record WalletResponse(UUID id,
                             String accountNumber,
                             BigDecimal balance,
                             Currency currency,
                             String ownerName,
                             boolean isActive,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt) {
}
