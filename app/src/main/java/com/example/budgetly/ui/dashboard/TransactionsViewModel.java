package com.example.budgetly.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.budgetly.main.entities.TransactionEntity;
import com.example.budgetly.main.services.TransactionsService;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TransactionsViewModel extends ViewModel {

    private final TransactionsService transactionsService;

    @Inject
    public TransactionsViewModel(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    public LiveData<String> getTransactionsAndUpdateView() {
        List<TransactionEntity> transactionEntities = transactionsService.getAllTransactions();
        return new MutableLiveData<>(transactionEntities.toString());
    }
}