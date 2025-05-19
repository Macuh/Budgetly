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

    public List<TransactionEntity> getAllTransactionsOrderByDescentDate() {
        return transactionDao.getAllTransactionOrderByDescentDate();
    }
}
