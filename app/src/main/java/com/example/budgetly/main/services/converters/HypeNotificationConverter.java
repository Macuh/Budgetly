package com.example.budgetly.main.services.converters;

import android.util.Log;

import com.example.budgetly.main.enums.BankNames;
import com.example.budgetly.main.enums.TransactionTypes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HypeNotificationConverter extends BankingAppNotificationConverter {

    @Override
    protected BankNames getBank() {
        return BankNames.HYPE;
    }

    @Override
    protected String getRecipient(String title, String text) {
        return title;
    }

    @Override
    protected Double getCost(String title, String text) {
        Pattern pattern = Pattern.compile("(\\d+([,.]\\d{1,2})?)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String numberString = matcher.group(1);

            if (numberString != null) {
                numberString = numberString.replace(",", ".").replaceAll("[^\\d.]", "");

                try {
                    return Double.parseDouble(numberString);
                } catch (NumberFormatException e) {
                    Log.d(this.getClass().getName(), "getCost: Error parsing extracted number string: " + numberString + " - " + e.getMessage());
                    return null;
                }
            }
        }

        Log.d(this.getClass().getName(), "getCost: Could not find a valid cost in text: " + text);
        return null;
    }

    @Override
    protected TransactionTypes getTransactionType(String title, String text) {
        if (text.toLowerCase().contains("hai ricevuto")) {
            return TransactionTypes.INGOING;
        } else {
            return TransactionTypes.OUTGOING;
        }
    }
}
