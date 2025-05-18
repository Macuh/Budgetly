package com.example.budgetly.main.services.converters;

import android.util.Log;

import com.example.budgetly.main.enums.BankNames;
import com.example.budgetly.main.enums.TransactionTypes;

public class TradeRepublicNotificationConverter extends BankingAppNotificationConverter {
    @Override
    protected BankNames getBank() {
        return BankNames.TRADE_REPUBLIC;
    }

    @Override
    protected String getRecipient(String title, String text) {
        return title;
    }

    @Override
    protected Double getCost(String title, String text) {
        try {
            String[] splitText = text.split(" ");
            String stringCost = splitText[1];
            return Double.valueOf(stringCost);
        } catch (Exception e) {
            Log.d(this.getClass().getName(), "getCost: Error retrieving cost from text" + e.getMessage());
            return null;
        }
    }

    @Override
    protected TransactionTypes getTransactionType(String title, String text) {
        try {
            String[] splitText = text.split(" ");
            String stringType = splitText[0];

            if(stringType.equalsIgnoreCase("received"))
                return TransactionTypes.INGOING;
            else
                return TransactionTypes.OUTGOING;
        } catch (Exception e) {
            Log.d(this.getClass().getName(), "getTransactionType: Error retrieving transactionType from text" + e.getMessage());
            return null;
        }
    }
}
