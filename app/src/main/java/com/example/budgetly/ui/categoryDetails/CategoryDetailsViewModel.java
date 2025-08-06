package com.example.budgetly.ui.categoryDetails;

import androidx.lifecycle.ViewModel;

import com.example.budgetly.main.dto.CategoryDto;
import com.example.budgetly.main.dto.TransactionEntryDto;
import com.example.budgetly.main.entities.TransactionWithCategory;
import com.example.budgetly.main.services.CategoriesService;
import com.example.budgetly.main.services.TransactionsService;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CategoryDetailsViewModel extends ViewModel {
    private final TransactionsService transactionsService;
    private final CategoriesService categoriesService;

    @Inject
    public CategoryDetailsViewModel(TransactionsService transactionsService, CategoriesService categoriesService) {
        this.transactionsService = transactionsService;
        this.categoriesService = categoriesService;
    }

    public List<TransactionEntryDto> retrieveTransactionsByCategoryIdAndMonth(String yearAndMonth, String categoryId) {
        List<TransactionWithCategory> transactionsWithCategory = transactionsService.getAllTransactionByMonthByCategoryIdOrderByDescentDate(yearAndMonth, categoryId);
        return transactionsWithCategory.stream().map(TransactionEntryDto::new).collect(Collectors.toList());
    }

    public String retrieveCategoryName(String categoryId) {
        CategoryDto categoryDto = categoriesService.getCategoryById(categoryId);

        if (categoryDto == null)
            return "No Name";

        return categoryDto.getCategoryName();
    }
}
