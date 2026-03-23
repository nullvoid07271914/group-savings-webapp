package com.groupsavings.utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class PaymentUtils {

	private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	private static final SecureRandom random = new SecureRandom();

	private static final int CODE_LENGTH = 8;

	public static String buildPaymentCode() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String dateTimePart = now.format(formatter); // This gives: 20260215

		String randomPart = generateRandomAlphanumeric();
		return "P" + dateTimePart + randomPart;
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
