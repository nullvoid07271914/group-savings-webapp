package com.groupsavings.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class LoanUtils {

	private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	private static final SecureRandom random = new SecureRandom();

	private static final int CODE_LENGTH = 8;

	public static String buildLoanCode() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String dateTimePart = now.format(formatter); // This gives: 20260215

		String randomPart = generateRandomAlphanumeric();
		return dateTimePart + randomPart;
	}

	public static BigDecimal toDecimal(String percentage) {
		if (percentage == null || percentage.trim().isEmpty()) {
			return BigDecimal.ZERO;
		}

		String clean = percentage.replace("%", "").trim();

		BigDecimal value = new BigDecimal(clean);
		return value.divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);
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
