package com.example.budgetly.main.repositories;

import android.app.Application;

import com.example.budgetly.main.DAOs.CategoryDao;
import com.example.budgetly.main.configurations.AppDatabase;
import com.example.budgetly.main.entities.CategoryEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class CategoryRepository {
    private final CategoryDao categoryDao;
    private final ExecutorService executorService;

    @Inject
    public CategoryRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        categoryDao = database.categoryDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(CategoryEntity categoryEntity) {
        executorService.execute(() -> categoryDao.insert(categoryEntity));
    }
}
