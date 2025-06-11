package com.example.budgetly.ui.dashboard;

import androidx.lifecycle.ViewModel;

import com.example.budgetly.main.dto.TransactionEntryDto;
import com.example.budgetly.main.dto.TransactionSummaryDto;
import com.example.budgetly.main.entities.TransactionWithCategory;
import com.example.budgetly.main.services.TransactionsService;
import com.example.budgetly.main.utils.TransactionUtils;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TransactionsViewModel extends ViewModel {

    private final TransactionsService transactionsService;

    @Inject
    public TransactionsViewModel(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    public TransactionSummaryDto getTransactionsSummaryByMonth(String yearAndMonth) {
        TransactionSummaryDto transactionsSummary = new TransactionSummaryDto();
        List<TransactionWithCategory> transactionEntities = transactionsService.getAllTransactionsByMonthOrderByDescentDate(yearAndMonth);

        transactionsSummary.setTotalCost((double) TransactionUtils.sumTransactions(transactionEntities.stream().map(TransactionEntryDto::new).collect(Collectors.toList())));
        transactionsSummary.setTransactions(transactionEntities.stream().map(TransactionEntryDto::new).collect(Collectors.toList()));

        return transactionsSummary;
    }

    public List<String> getAllTransactionMonths() {
        return transactionsService.getAllTransactionMonths();
    }
}