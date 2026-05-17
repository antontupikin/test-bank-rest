package com.example.bankcards.scenario.wallet;

import com.example.bankcards.controller.request.WalletUpdateBalanceRequest;
import com.example.bankcards.controller.response.WalletResponse;
import com.example.bankcards.entity.OperationResult;
import com.example.bankcards.entity.OperationType;
import com.example.bankcards.entity.Wallet;
import com.example.bankcards.exception.ForbiddenException;
import com.example.bankcards.exception.InsufficientFundsException;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.mapper.WalletMapper;
import com.example.bankcards.service.UserService;
import com.example.bankcards.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class WalletUpdateScenario {
    private final WalletService walletService;
    private final WalletMapper walletMapper;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public WalletResponse updateBalance(WalletUpdateBalanceRequest request, Long userId) {
        log.info("Update balance for user with id {} and wallet id {} started", userId, request.walletId());
        UUID operationId = UUID.randomUUID();
        OperationType operationType = request.operationType();
        OperationResult result = switch (operationType) {
            case DEPOSIT -> walletService.deposit(request.walletId(), userId, request.amount(), operationId);
            case WITHDRAW -> walletService.withdraw(request.walletId(), userId, request.amount(), operationId);
        };

        if (result == OperationResult.NOT_FOUND) {
            throw new NotFoundException("message.exception.not-found.user.wallet");
        }
        if (result == OperationResult.FORBIDDEN) {
            throw new ForbiddenException("message.exception.not-found.user.wallet");
        }
        if (result == OperationResult.INSUFFICIENT_FUNDS) {
            throw new InsufficientFundsException("message.exception.wallet.not-enough");
        }

        log.info("Update balance for user with id {} and wallet id {} finished", userId, request.walletId());
        Wallet wallet = walletService.getByIdAndUserId(request.walletId(), userId);
        return walletMapper.toWalletResponse(wallet);
    }
}
