package com.example.budgetly.main.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.budgetly.main.entities.TransactionEntity;

import java.util.List;

@Dao
public interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY transaction_date DESC")
    List<TransactionEntity> getAllTransactionOrderByDescentDate();

    @Insert
    void insert(TransactionEntity transaction);

    @Update
    void update(TransactionEntity transaction);
}
