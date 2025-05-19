package com.example.budgetly.main.services.converters;

import android.util.Log;

import com.example.budgetly.main.enums.BankNames;
import com.example.budgetly.main.enums.TransactionTypes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TradeRepublicNotificationConverter extends BankingAppNotificationConverter {
    private Matcher getTextMatcher(String text) throws Exception {
        Pattern pattern = Pattern.compile("^(\\w+) €(\\d+\\.?\\d*)");
        Matcher matcher = pattern.matcher(text);

        if(!matcher.find())
            throw new Exception("Error creating text matcher");

        return matcher;
    }

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
            Matcher matcher = getTextMatcher(text);
            String costStr = matcher.group(2);

            if(costStr == null)
                throw new Exception("Error converting cost string");

            return Double.valueOf(costStr);
        } catch (Exception e) {
            Log.d(this.getClass().getName(), "getCost: Error retrieving cost from text" + e.getMessage());
            return null;
        }
    }

    @Override
    protected TransactionTypes getTransactionType(String title, String text) {
        try {
            Matcher matcher = getTextMatcher(text);
            String transactionTypeStr = matcher.group(1);

            if(transactionTypeStr == null)
                throw new Exception("Error converting transactionType string");

            if(transactionTypeStr.equalsIgnoreCase("received"))
                return TransactionTypes.INGOING;
            else
                return TransactionTypes.OUTGOING;
        } catch (Exception e) {
            Log.d(this.getClass().getName(), "getTransactionType: Error retrieving transactionType from text" + e.getMessage());
            return null;
        }
    }
}
