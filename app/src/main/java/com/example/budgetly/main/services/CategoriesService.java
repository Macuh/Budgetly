package com.example.budgetly.main.services;

import com.example.budgetly.main.dto.CategoryDto;
import com.example.budgetly.main.entities.CategoryEntity;
import com.example.budgetly.main.repositories.CategoryRepository;
import com.example.budgetly.main.repositories.TransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class CategoriesService {
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    @Inject
    public CategoriesService(CategoryRepository categoryRepository, TransactionRepository transactionRepository) {
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<CategoryDto> getCategoriesWithExpensesByMonth(String yearAndMonth) {
        List<CategoryDto> categoryDtos = new ArrayList<>();

        List<CategoryEntity> categories = categoryRepository.getAllCategories();
        Map<Long, Double> categoryExpenses = transactionRepository.getAllCategoriesExpensesByMonth(yearAndMonth);

        for(CategoryEntity category : categories) {
            Double expensesValue = categoryExpenses.get(category.getCategoryId());
            expensesValue = expensesValue != null ? expensesValue : 0.0D;

            categoryDtos.add(new CategoryDto(category.getCategoryId(), category.getCategoryName(), expensesValue));
        }

        return categoryDtos;
    }

    public List<CategoryDto> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.getAllCategories();
        return categories.stream().map(CategoryDto::new).collect(Collectors.toList());
    }
}
