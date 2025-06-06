package com.example.budgetly.main.repositories;

import android.app.Application;

import com.example.budgetly.main.DAOs.TransactionDao;
import com.example.budgetly.main.configurations.AppDatabase;
import com.example.budgetly.main.entities.TransactionEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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
        return transactionDao.getAllTransactionByMonthOrderByDescentDate(yearAndMonth);
    }

    public List<String> getAllTransactionMonths() {
        return transactionDao.getAllTransactionMonths();
    }

    public TransactionEntity getTransactionById(Long transactionId) {
        return transactionDao.getTransactionById(transactionId);
    }

    public Double getDailyExpenses(String yearMonthDay) {
        return transactionDao.getDailyExpensesSum(yearMonthDay);
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

    public Map<Long, Double> getAllCategoriesExpensesByMonth(String yearAndMonth) {
        List<TransactionEntity> categoryExpensesDtos = transactionDao.getAllTransactionByMonthOrderByDescentDate(yearAndMonth);

        return categoryExpensesDtos.stream()
                .collect(Collectors.groupingBy(
                        TransactionEntity::getCategory,
                        Collectors.summingDouble(TransactionEntity::getCost)
                ));
    }
}
