package com.example.budgetly.main.enums;

public enum BankNames {
    TRADE_REPUBLIC("Trade Republic"),
    HYPE("Hype"),
    REVOLUT("Revolut"),
    MONEY("Money");

    private final String displayName;

    BankNames(String displayName) {
        this.displayName = displayName;
    }

    public static BankNames fromString(String input) {
        if (input == null) return null;
        try {
            return BankNames.valueOf(input.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public String getDisplayName() {
        return displayName;
    }
}
