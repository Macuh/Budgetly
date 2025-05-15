package com.example.budgetly.main.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.budgetly.main.entities.CategoryEntity;

@Dao
public interface CategoryDao {
    @Insert
    void insert(CategoryEntity categoryEntity);
}
