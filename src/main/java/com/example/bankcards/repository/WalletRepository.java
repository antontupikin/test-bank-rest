package com.example.bankcards.repository;

import com.example.bankcards.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    boolean existsByIdAndUserId(UUID walletId, Long userId);

    Optional<Wallet> findByIdAndUserId(UUID id, Long userId);

    @Modifying
    @Query("""
                update Wallet w
                set w.balance = w.balance + :amount
                where w.id = :walletId
                  and w.user.id = :userId
            """)
    int deposit(
            @Param("walletId") UUID walletId,
            @Param("userId") Long userId,
            @Param("amount") BigDecimal amount
    );

    @Modifying
    @Query("""
                update Wallet w
                set w.balance = w.balance - :amount
                where w.id = :walletId
                  and w.user.id = :userId
                  and w.balance >= :amount
            """)
    int withdraw(
            @Param("walletId") UUID walletId,
            @Param("userId") Long userId,
            @Param("amount") BigDecimal amount
    );
}