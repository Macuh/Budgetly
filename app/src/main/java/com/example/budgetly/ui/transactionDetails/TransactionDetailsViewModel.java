package com.example.budgetly.ui.transactionDetails;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.budgetly.main.dto.TransactionEntryDto;
import com.example.budgetly.main.enums.TransactionTypes;
import com.example.budgetly.main.services.TransactionsService;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TransactionDetailsViewModel extends ViewModel {
    private final TransactionsService transactionsService;

    @Inject
    public TransactionDetailsViewModel(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    public TransactionEntryDto getTransactionById(String transactionId) {
        try {
            if(transactionId != null)
                return transactionsService.getTransactionById(Long.valueOf(transactionId));
        } catch (Exception e) {
            Log.e("TransactionDetailsViewModel.getTransactionById", "getTransactionById: ", e);
        }

        return null;
    }

    public List<TransactionEntryDto> getAllTransactionByRecipient(String yearAndMonth, String recipient) {
        try {
            return transactionsService.getAllTransactionByRecipientOrderByDescentDate(yearAndMonth, recipient);
        } catch (Exception e) {
            Log.e("TransactionDetailsViewModel.getAllTransactionByRecipient", "getAllTransactionByRecipient: ", e);
        }

        return Collections.emptyList();
    }

    public Double getAllRecipientExpenses(List<TransactionEntryDto> recipientTransactions) {
        return recipientTransactions.stream()
                .filter(transactionEntryDto -> transactionEntryDto.getTransactionType().equals(TransactionTypes.OUTGOING))
                .mapToDouble(TransactionEntryDto::getCost).sum();
    }

    public Double getAllRecipientRevenues(List<TransactionEntryDto> recipientTransactions) {
        return recipientTransactions.stream()
                .filter(transactionEntryDto -> transactionEntryDto.getTransactionType().equals(TransactionTypes.INGOING))
                .mapToDouble(TransactionEntryDto::getCost).sum();
    }
}
