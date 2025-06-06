package com.example.budgetly.ui.categories;

import androidx.lifecycle.ViewModel;

import com.example.budgetly.main.dto.CategoryDto;
import com.example.budgetly.main.services.CategoriesService;
import com.example.budgetly.main.services.TransactionsService;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CategoriesViewModel extends ViewModel {

    private final TransactionsService transactionsService;
    private final CategoriesService categoriesService;

    public List<CategoryDto> getCategoriesWithExpensesByMonth(String yearAndMonth) {
        return categoriesService.getCategoriesWithExpensesByMonth(yearAndMonth);
    }

    @Inject
    public CategoriesViewModel(TransactionsService transactionsService, CategoriesService categoriesService) {
        this.transactionsService = transactionsService;
        this.categoriesService = categoriesService;
    }

    public List<String> getAllTransactionMonths() {
        return transactionsService.getAllTransactionMonths();
    }
}