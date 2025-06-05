package com.example.budgetly.main.listeners;

import android.view.View;

import com.example.budgetly.main.entities.CategoryEntity;
import com.example.budgetly.main.repositories.CategoryRepository;

import javax.inject.Inject;

public class CreateNewCategoryClickListener implements View.OnClickListener {
    private final CategoryRepository categoryRepository;

    @Inject
    public CreateNewCategoryClickListener(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void onClick(View v) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName("name");
        categoryRepository.insert(categoryEntity);
    }
}
