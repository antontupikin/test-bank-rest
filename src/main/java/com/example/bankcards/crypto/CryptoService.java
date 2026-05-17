package com.example.bankcards.crypto;

import com.example.bankcards.crypto.source.EncryptionService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class CryptoService {
    private final ObjectMapper objectMapper;

    public CryptoService(CryptoProperties cryptoProperties) {
        this.objectMapper = EncryptionService.getInstance(cryptoProperties.getPassword());
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @SneakyThrows
    public String encrypt(Object object) {
        return objectMapper.writeValueAsString(object);
    }

    @SneakyThrows
    public <T> T decrypt(String json, Class<T> clazz) {
        return objectMapper.readValue(json, clazz);
    }
}
