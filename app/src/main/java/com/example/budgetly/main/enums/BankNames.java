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

    public String getDisplayName() {
        return displayName;
    }
}
