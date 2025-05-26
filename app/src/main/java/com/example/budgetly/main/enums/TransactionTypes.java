package com.example.budgetly.main.enums;

public enum TransactionTypes {
    INGOING,
    OUTGOING;

    public static TransactionTypes fromString(String input) {
        if (input == null) return null;
        try {
            return TransactionTypes.valueOf(input.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
