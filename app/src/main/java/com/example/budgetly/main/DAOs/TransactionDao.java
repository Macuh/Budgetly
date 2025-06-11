package com.example.budgetly.main.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.budgetly.main.entities.TransactionEntity;
import com.example.budgetly.main.entities.TransactionWithCategory;

import java.util.List;

@Dao
public interface TransactionDao {
    @Transaction
    @Query("SELECT * FROM transactions where id = :id")
    TransactionWithCategory getTransactionById(Long id);

    @Query("DELETE FROM transactions WHERE id = :transactionId")
    int deleteById(Long transactionId);

    @Transaction
    @Query("SELECT * FROM transactions WHERE strftime('%Y-%m', datetime(transaction_date, 'unixepoch')) = :yearAndMonth ORDER BY transaction_date DESC")
    List<TransactionWithCategory> getAllTransactionByMonthOrderByDescentDate(String yearAndMonth);

    @Query("SELECT * FROM transactions WHERE strftime('%Y-%m', datetime(transaction_date, 'unixepoch')) = :yearAndMonth AND LOWER(transaction_recipient) = LOWER(:recipient) ORDER BY transaction_date DESC")
    List<TransactionEntity> getAllTransactionByRecipientOrderByDescentDate(String yearAndMonth, String recipient);

    @Query("SELECT DISTINCT strftime('%Y-%m', datetime(transaction_date, 'unixepoch')) AS month FROM transactions ORDER BY month DESC")
    List<String> getAllTransactionMonths();

    @Query("SELECT * FROM transactions WHERE strftime('%Y-%m-%d', datetime(transaction_date, 'unixepoch')) = :yearMonthDay")
    List<TransactionEntity> getDailyYTransaction(String yearMonthDay);

    @Insert
    void insert(TransactionEntity transaction);

    @Update
    void update(TransactionEntity transaction);
}
