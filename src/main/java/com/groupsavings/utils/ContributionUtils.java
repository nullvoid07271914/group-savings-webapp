package com.groupsavings.utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class ContributionUtils {

    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final SecureRandom random = new SecureRandom();

    private static final int CODE_LENGTH = 8;

    public static String buildContributionCode() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String dateTimePart = now.format(formatter); // This gives: 20260215-100523

        String randomPart = generateRandomAlphanumeric();

        String datePart = dateTimePart.substring(0, 8); // 20260215
        String timePart = dateTimePart.substring(9); // 100523

        return datePart + randomPart + timePart;
    }

    private static String generateRandomAlphanumeric() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(ALPHANUMERIC.length());
            sb.append(ALPHANUMERIC.charAt(index));
        }
        return sb.toString();
    }
}
