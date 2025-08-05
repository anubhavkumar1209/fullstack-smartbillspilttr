package com.smartbillsplittr.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

public class CalculationUtil {

    public static BigDecimal splitEqually(BigDecimal totalAmount, int numberOfPeople) {
        if (numberOfPeople <= 0) {
            throw new IllegalArgumentException("Number of people must be greater than 0");
        }
        return totalAmount.divide(new BigDecimal(numberOfPeople), 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculatePercentage(BigDecimal amount, BigDecimal percentage) {
        return amount.multiply(percentage).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateNetBalance(BigDecimal totalPaid, BigDecimal totalOwed) {
        return totalPaid.subtract(totalOwed);
    }

    public static boolean isBalanceSettled(BigDecimal balance) {
        return balance.compareTo(BigDecimal.ZERO) == 0;
    }

    public static BigDecimal roundToTwoDecimals(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal sumAmounts(List<BigDecimal> amounts) {
        return amounts.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static Map<String, BigDecimal> calculateSplitByPercentage(
            BigDecimal totalAmount, Map<String, BigDecimal> percentages) {
        Map<String, BigDecimal> result = new java.util.HashMap<>();

        for (Map.Entry<String, BigDecimal> entry : percentages.entrySet()) {
            BigDecimal splitAmount = calculatePercentage(totalAmount, entry.getValue());
            result.put(entry.getKey(), splitAmount);
        }

        return result;
    }
}
