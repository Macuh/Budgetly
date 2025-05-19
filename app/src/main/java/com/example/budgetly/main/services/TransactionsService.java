package com.example.budgetly.main.services;

import com.example.budgetly.main.entities.TransactionEntity;
import com.example.budgetly.main.repositories.TransactionRepository;

import java.util.List;

import javax.inject.Inject;

public class TransactionsService {
    private final TransactionRepository transactionRepository;

    @Inject
    public TransactionsService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionEntity> getAllTransactionsOrderByDescentDate() {
        return transactionRepository.getAllTransactionsOrderByDescentDate();
    }
}
