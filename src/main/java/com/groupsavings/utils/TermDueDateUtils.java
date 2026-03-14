package com.groupsavings.utils;

import java.time.LocalDate;

public abstract class TermDueDateUtils {

	public static LocalDate calculateDueDate(LocalDate currentDate, int terms) {
		if (terms == 0)
			return currentDate;

		LocalDate dueDate = currentDate;

		for (int i = 0; i < terms; i++) {
			dueDate = addOneTerm(dueDate);
		}

		return dueDate;
	}

	private static LocalDate addOneTerm(LocalDate date) {
		int day = date.getDayOfMonth();

		// If 1st term (days 1-15)
		if (day <= 15) {
			// Move to 2nd term of same month (last day or 30/31)
			return date.withDayOfMonth(getLastDayOfMonth(date));
		}
		// If 2nd term (days 16-31)
		else {
			// Move to next month, 1st term (day 15)
			LocalDate nextMonth = date.plusMonths(1);
			return nextMonth.withDayOfMonth(15);
		}
	}

	private static int getLastDayOfMonth(LocalDate date) {
		return date.lengthOfMonth(); // Returns 28, 29, 30, 31 depending on month
	}
}
