package com.example.bankcards.mapper;

import com.example.bankcards.controller.response.WalletResponse;
import com.example.bankcards.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WalletMapper {
    WalletResponse toWalletResponse(Wallet wallet);

}
