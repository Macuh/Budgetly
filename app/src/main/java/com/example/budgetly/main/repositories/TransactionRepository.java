package com.example.budgetly.main.repositories;

import android.app.Application;

import com.example.budgetly.main.DAOs.TransactionDao;
import com.example.budgetly.main.configurations.AppDatabase;
import com.example.budgetly.main.entities.TransactionEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class TransactionRepository {
    private final TransactionDao transactionDao;
    private final ExecutorService executorService;


    @Inject
    public TransactionRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        transactionDao = database.transactionDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(TransactionEntity transaction) {
        executorService.execute(() -> transactionDao.insert(transaction));
    }

    public List<TransactionEntity> getAllTransactionsByMonthOrderByDescentDate(String yearAndMonth) {
        return transactionDao.getAllTransactionOrderByDescentDate(yearAndMonth);
    }

    public List<String> getAllTransactionMonths() {
        return transactionDao.getAllTransactionMonths();
    }

    public TransactionEntity getTransactionById(Long transactionId) {
        return transactionDao.getTransactionById(transactionId);
    }

    public List<TransactionEntity> getAllTransactionByRecipientOrderByDescentDate(String yearAndMonth, String recipient) {
        return transactionDao.getAllTransactionByRecipientOrderByDescentDate(yearAndMonth, recipient);
    }

    public boolean deleteById(Long transactionId) {
        return transactionDao.deleteById(transactionId) > 0;
    }

    public void update(TransactionEntity transaction) {
        transactionDao.update(transaction);
    }
}
