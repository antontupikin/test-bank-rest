package com.example.bankcards.service;

import com.example.bankcards.entity.OperationResult;
import com.example.bankcards.entity.OperationType;
import com.example.bankcards.entity.Wallet;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;
    private final RetryTemplate retryTemplate = RetryTemplate.builder()
            .maxAttempts(3)
            .fixedBackoff(50)
            .retryOn(DeadlockLoserDataAccessException.class)
            .retryOn(CannotAcquireLockException.class)
            .build();

    public Wallet getByIdAndUserId(UUID id, Long userId) {
        return walletRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.user.wallet"));
    }

    public OperationResult deposit(UUID walletId, Long userId, BigDecimal amount, UUID operationId) {
        int updated = retryTemplate.execute(ctx -> walletRepository.deposit(walletId, userId, amount));
        if (updated == 0) {
            return walletRepository.existsById(walletId) ? OperationResult.FORBIDDEN : OperationResult.NOT_FOUND;
        }
        walletTransactionService.saveTransaction(walletId, amount, OperationType.DEPOSIT,operationId);
        return OperationResult.SUCCESS;
    }

    public OperationResult withdraw(UUID walletId, Long userId, BigDecimal amount, UUID operationId) {
        int updated = retryTemplate.execute(ctx -> walletRepository.withdraw(walletId, userId, amount));
        if (updated == 0) {
            if (!walletRepository.existsById(walletId)) {
                return OperationResult.NOT_FOUND;
            }
            if (!walletRepository.existsByIdAndUserId(walletId, userId)) {
                return OperationResult.FORBIDDEN;
            }
            return OperationResult.INSUFFICIENT_FUNDS;
        }
        walletTransactionService.saveTransaction(walletId, amount, OperationType.WITHDRAW, operationId);
        return OperationResult.SUCCESS;
    }
}