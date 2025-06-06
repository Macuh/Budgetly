package com.example.budgetly.main.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.budgetly.main.entities.CategoryEntity;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    void insert(CategoryEntity categoryEntity);

    @Query("Select * FROM category")
    List<CategoryEntity> getAllCategories();
}
