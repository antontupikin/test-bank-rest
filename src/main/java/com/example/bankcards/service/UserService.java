package com.example.bankcards.service;

import com.example.bankcards.entity.User;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.FieldsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("message.exception.not-found.user"));
    }

    public User get(Principal principal) {
        return getByEmail(principal.getName());
    }

    public User getByEmail(String email) {
        return findByEmail(email).orElseThrow(() -> new NotFoundException("message.exception.not-found.user"));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(FieldsUtils.normalizeEmail(email));
    }
}
