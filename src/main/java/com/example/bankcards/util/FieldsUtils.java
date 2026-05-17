package com.example.bankcards.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FieldsUtils {
    public static final String PHONE_PATTERN = "^(\\+?[78])?\\d{10}$";
    public static final String WORD_PATTERN = "^[а-яА-ЯЁё-]+$";
    public static final String METRIC_PATTERN = "^[a-z_0-9]+$";

    public static boolean isPhoneNumber(String string) {
        return string.matches(PHONE_PATTERN);
    }

    public static String normalizePhone(String phone) {
        phone = phone.trim().replaceAll("^\\+", "");
        if (phone.length() == 10) {
            return "7" + phone;
        } else {
            return phone.replaceAll("^8", "7");
        }
    }

    public static String normalizeName(String name) {
        return name.trim();
    }

    public static String normalizeSource(String source) {
        return source.trim();
    }

    public static String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

    public static String normalizeText(String text) {
        return text.trim();
    }

    public static String normalizeLogin(String login) {
        return login.trim().toLowerCase();
    }

    public static String normalizeWord(String word) {
        return word.trim().replaceAll("\\s+", " ").toLowerCase();
    }
}
