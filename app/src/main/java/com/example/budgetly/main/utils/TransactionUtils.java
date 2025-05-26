package com.example.budgetly.main.utils;

import com.example.budgetly.main.dto.TransactionEntryDto;
import com.example.budgetly.main.enums.TransactionTypes;

import java.util.List;

public class TransactionUtils {
    public static float sumTransactions(List<TransactionEntryDto> dailyTransactions) {
        float expenses = 0;

        for(TransactionEntryDto dailyTransaction : dailyTransactions) {
            if(dailyTransaction.getTransactionType().equals(TransactionTypes.OUTGOING))
                expenses = expenses + dailyTransaction.getCost().floatValue();
            else
                expenses = expenses - dailyTransaction.getCost().floatValue();
        }

        return expenses;
    }
}
