package com.example.budgetly.main.services;

import com.example.budgetly.main.dto.CategoryDto;
import com.example.budgetly.main.entities.CategoryEntity;
import com.example.budgetly.main.repositories.CategoryRepository;
import com.example.budgetly.main.repositories.TransactionRepository;

import java.util.ArrayList;
import java.util.Comparator;
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

    public CategoryDto getCategoryById(String categoryId) {
        return new CategoryDto(categoryRepository.getCategoryById(Long.valueOf(categoryId)));
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

        // Desc Sort By total expenses
        return categoryDtos.stream().sorted(Comparator.comparing(CategoryDto::getTotalExpenses).reversed()).collect(Collectors.toList());
    }

    public List<CategoryDto> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.getAllCategories();
        return categories.stream().map(CategoryDto::new).collect(Collectors.toList());
    }
}
