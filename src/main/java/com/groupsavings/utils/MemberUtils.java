package com.groupsavings.utils;

import java.time.LocalDate;
import java.util.UUID;

public abstract class MemberUtils {

    public static String buildMemberCode() {
        int currentYear = LocalDate.now().getYear();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        return String.valueOf(currentYear) + uuid.substring(0, 8);
    }
}
