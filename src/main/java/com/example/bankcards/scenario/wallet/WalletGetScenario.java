package com.example.bankcards.scenario.wallet;

import com.example.bankcards.controller.response.WalletResponse;
import com.example.bankcards.entity.Wallet;
import com.example.bankcards.mapper.WalletMapper;
import com.example.bankcards.service.UserService;
import com.example.bankcards.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Manager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class WalletGetScenario {
    private final UserService userService;
    private final WalletService walletService;
    private final WalletMapper walletMapper;

    @Transactional(readOnly = true)
    public WalletResponse findByIdAndUser(UUID id, Long userId) {
        log.info("Find wallet with id {} for user", id);
        Wallet wallet = walletService.getByIdAndUserId(id, userId);
        return walletMapper.toWalletResponse(wallet);
    }
}
