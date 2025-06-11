package com.example.budgetly.main.services;

import com.example.budgetly.main.dto.TransactionEntryDto;
import com.example.budgetly.main.entities.TransactionEntity;
import com.example.budgetly.main.entities.TransactionWithCategory;
import com.example.budgetly.main.repositories.TransactionRepository;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class TransactionsService {
    private final TransactionRepository transactionRepository;

    @Inject
    public TransactionsService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionWithCategory> getAllTransactionsByMonthOrderByDescentDate(String yearAndMonth) {
        return transactionRepository.getAllTransactionsByMonthOrderByDescentDate(yearAndMonth);
    }

    public List<TransactionEntryDto> getAllTransactionByRecipientOrderByDescentDate(String yearAndMonth, String recipient) {
        return transactionRepository.getAllTransactionByRecipientOrderByDescentDate(yearAndMonth, recipient)
                .stream().map(TransactionEntryDto::new).collect(Collectors.toList());
    }

    public List<String> getAllTransactionMonths() {
        return transactionRepository.getAllTransactionMonths();
    }

    public TransactionEntryDto getTransactionById(String transactionId) {
        TransactionEntity transaction = null;

        if (transactionId != null)
            transaction = transactionRepository.getTransactionById(Long.valueOf(transactionId));

        return transaction != null ? new TransactionEntryDto(transaction) : null;
    }

    public boolean saveTransaction(TransactionEntryDto transactionEntryDto) {
        try {
            TransactionEntity transactionEntity = new TransactionEntity(transactionEntryDto);
            transactionRepository.update(transactionEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
