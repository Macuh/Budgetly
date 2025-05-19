package com.example.budgetly.ui.dashboard;

import androidx.lifecycle.ViewModel;

import com.example.budgetly.main.dto.TransactionEntryDto;
import com.example.budgetly.main.entities.TransactionEntity;
import com.example.budgetly.main.services.TransactionsService;

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

    public List<TransactionEntryDto> getTransactionsAndUpdateView() {
        List<TransactionEntity> transactionEntities = transactionsService.getAllTransactionsOrderByDescentDate();
        return transactionEntities.stream().map(TransactionEntryDto::new).collect(Collectors.toList());
    }
}