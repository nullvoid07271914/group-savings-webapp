package com.groupsavings.mapper.custom;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class BaseMapper {

    protected String getString(Object[] row, BaseColumnIndex col) {
        return row[col.getIndex()] != null ? (String) row[col.getIndex()] : null;
    }

    protected BigDecimal getBigDecimal(Object[] row, BaseColumnIndex col) {
        Object value = row[col.getIndex()];
        if (value == null)
            return null;

        // Handle BigDecimal directly
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }

        // Handle Double (from MySQL SUM, addition, etc.)
        if (value instanceof Double) {
            return BigDecimal.valueOf((Double) value);
        }

        // Handle Integer
        if (value instanceof Integer) {
            return BigDecimal.valueOf((Integer) value);
        }

        // Handle Long
        if (value instanceof Long) {
            return BigDecimal.valueOf((Long) value);
        }

        // Handle Float
        if (value instanceof Float) {
            return BigDecimal.valueOf((Float) value);
        }

        // Handle String (just in case)
        if (value instanceof String) {
            try {
                return new BigDecimal((String) value);
            } catch (NumberFormatException e) {
                return BigDecimal.ZERO;
            }
        }

        // Default return
        return BigDecimal.ZERO;
    }

    protected Integer getInt(Object[] row, BaseColumnIndex col) {
        return row[col.getIndex()] != null ? ((Number) row[col.getIndex()]).intValue() : null;
    }

    protected Float getFloat(Object[] row, BaseColumnIndex col) {
        return row[col.getIndex()] != null ? ((Number) row[col.getIndex()]).floatValue() : null;
    }

    protected LocalDate getLocalDate(Object[] row, BaseColumnIndex col) {
        Object date = row[col.getIndex()];
        if (date == null)
            return null;

        if (date instanceof java.sql.Date) {
            return ((java.sql.Date) date).toLocalDate();
        }
        if (date instanceof java.sql.Timestamp) {
            return ((java.sql.Timestamp) date).toLocalDateTime().toLocalDate();
        }
        if (date instanceof LocalDate) {
            return (LocalDate) date;
        }

        return null;
    }
}
