package com.example.bankcards.service;

import com.example.bankcards.entity.OperationType;
import com.example.bankcards.entity.WalletTransaction;
import com.example.bankcards.repository.WalletTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletTransactionService {
    private final WalletTransactionRepository walletTransactionRepository;

    public void saveTransaction(UUID walletId, BigDecimal amount, OperationType type, UUID operationId) {
        WalletTransaction walletTransaction = WalletTransaction.builder()
                .walletId(walletId)
                .operationId(operationId)
                .amount(amount)
                .operationType(type)
                .build();
        try {
            walletTransactionRepository.save(walletTransaction);
        } catch (DataIntegrityViolationException e) {
            // idempotent repeat of the same business operation
        }
    }
}
