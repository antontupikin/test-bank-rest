package com.example.bankcards.repository;

import com.example.bankcards.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletTransactionRepository
        extends JpaRepository<WalletTransaction, UUID> {
}
