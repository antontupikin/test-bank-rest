package com.example.bankcards.controller;

import com.example.bankcards.config.JwtAuthenticationFilter;
import com.example.bankcards.config.JwtService;
import com.example.bankcards.controller.response.WalletResponse;
import com.example.bankcards.entity.Currency;
import com.example.bankcards.entity.OperationType;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.scenario.wallet.WalletGetScenario;
import com.example.bankcards.scenario.wallet.WalletUpdateScenario;
import com.example.bankcards.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(excludeAutoConfiguration = SecurityAutoConfiguration.class, controllers = WalletController.class)
@AutoConfigureMockMvc(addFilters = false)
class WalletControllerTest {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.3-alpine")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test_db");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        postgres.start();
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void contextLoads() {
    }

    @MockitoBean
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WalletUpdateScenario walletUpdateScenario;

    @MockitoBean
    private WalletGetScenario walletGetScenario;

    @MockitoBean
    private UserService userService;

    @Test
    void changeShouldReturnOk() throws Exception {
        UUID walletId = UUID.randomUUID();
        Long userId = 100L;

        when(userService.get(any())).thenReturn(User.builder().id(userId).role(Role.USER).build());
        when(walletUpdateScenario.updateBalance(any(), anyLong())).thenReturn(walletResponse(walletId));

        var body = """
                {
                  "walletId": "%s",
                  "operationType": "%s",
                  "amount": 100.50
                }
                """.formatted(walletId, OperationType.DEPOSIT.name());

        mockMvc.perform(post("/api/v1/wallet")
                        .principal(() -> "user@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(walletId.toString()))
                .andExpect(jsonPath("$.balance").value(100.50));
    }

    @Test
    void findByIdshouldReturnOk() throws Exception {
        UUID walletId = UUID.randomUUID();
        Long userId = 100L;

        when(userService.get(any())).thenReturn(User.builder().id(userId).role(Role.USER).build());
        when(walletGetScenario.findByIdAndUser(any(), anyLong())).thenReturn(walletResponse(walletId));

        mockMvc.perform(get("/api/v1/wallet/{id}", walletId)
                        .principal(() -> "user@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(walletId.toString()))
                .andExpect(jsonPath("$.currency").value(Currency.RUB.name()));
    }

    private WalletResponse walletResponse(UUID walletId) {
        return new WalletResponse(
                walletId,
                "1234567890",
                new BigDecimal("100.50"),
                Currency.RUB,
                "User",
                true,
                LocalDateTime.of(2026, 1, 1, 0, 0),
                LocalDateTime.of(2026, 1, 1, 0, 0)
        );
    }
}

