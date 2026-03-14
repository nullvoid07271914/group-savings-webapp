package com.groupsavings.utils;

import java.time.LocalDate;
import java.util.UUID;

public abstract class SavingsPoolUtils {

    public static String buildSavingsCode() {
        int currentYear = LocalDate.now().getYear();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        return String.valueOf(currentYear) + uuid.substring(0, 12);
    }
}
