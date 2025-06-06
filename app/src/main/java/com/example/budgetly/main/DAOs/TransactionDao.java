package com.example.budgetly.main.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.budgetly.main.entities.TransactionEntity;

import java.util.List;

@Dao
public interface TransactionDao {
    @Query("SELECT * FROM transactions where id = :id")
    TransactionEntity getTransactionById(Long id);

    @Query("DELETE FROM transactions WHERE id = :transactionId")
    int deleteById(Long transactionId);

    @Query("SELECT * FROM transactions WHERE strftime('%Y-%m', datetime(transaction_date, 'unixepoch')) = :yearAndMonth ORDER BY transaction_date DESC")
    List<TransactionEntity> getAllTransactionByMonthOrderByDescentDate(String yearAndMonth);

    @Query("SELECT * FROM transactions WHERE strftime('%Y-%m', datetime(transaction_date, 'unixepoch')) = :yearAndMonth AND LOWER(transaction_recipient) = LOWER(:recipient) ORDER BY transaction_date DESC")
    List<TransactionEntity> getAllTransactionByRecipientOrderByDescentDate(String yearAndMonth, String recipient);

    @Query("SELECT DISTINCT strftime('%Y-%m', datetime(transaction_date, 'unixepoch')) AS month FROM transactions ORDER BY month DESC")
    List<String> getAllTransactionMonths();

    @Query("SELECT SUM(transactions.cost) FROM transactions WHERE strftime('%Y-%m-%d', datetime(transaction_date, 'unixepoch')) = :yearMonthDay")
    Double getDailyExpensesSum(String yearMonthDay);

    @Insert
    void insert(TransactionEntity transaction);

    @Update
    void update(TransactionEntity transaction);
}
