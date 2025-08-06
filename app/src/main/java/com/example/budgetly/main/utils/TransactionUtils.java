package com.example.budgetly.main.utils;

import com.example.budgetly.main.dto.TransactionEntryDto;
import com.example.budgetly.main.enums.TransactionTypes;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionUtils {
    public static float sumTransactions(List<TransactionEntryDto> transactions) {
        float expenses = 0;

        for(TransactionEntryDto transaction : transactions) {
            if(transaction.getTransactionType().equals(TransactionTypes.OUTGOING))
                expenses = expenses + transaction.getCost().floatValue();
            else
                expenses = expenses - transaction.getCost().floatValue();
        }

        return expenses;
    }

    public static String makePriceDisplayable(Double cost) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
        return cost != null ? currencyFormatter.format(cost) : "";
    }

    public static Map<Integer, List<TransactionEntryDto>> groupTransactionsByDay(List<TransactionEntryDto> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(transactionEntryDto -> transactionEntryDto.getDate().getDayOfMonth()));
    }

    public static List<Object> createListOfTransactionsDividedByDay(List<TransactionEntryDto> transactions) {
        Map<Integer, List<TransactionEntryDto>> groupedTransactionsByDay = groupTransactionsByDay(transactions);

        ArrayList<Object> listOfTransactionsDividedByDay = new ArrayList<>();

        groupedTransactionsByDay.entrySet().stream()
                .sorted(Map.Entry.<Integer, List<TransactionEntryDto>>comparingByKey().reversed())
                .forEach(entry -> {
                    listOfTransactionsDividedByDay.add(entry.getKey());
                    listOfTransactionsDividedByDay.addAll(entry.getValue());
                });

        return listOfTransactionsDividedByDay;
    }
}
