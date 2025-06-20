package com.example.budgetly.ui.editTransaction;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.budgetly.main.dto.CategoryDto;
import com.example.budgetly.main.dto.TransactionEntryDto;
import com.example.budgetly.main.enums.BankNames;
import com.example.budgetly.main.enums.TransactionTypes;
import com.example.budgetly.main.services.CategoriesService;
import com.example.budgetly.main.services.TransactionsService;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class EditTransactionViewModel extends ViewModel {
    private final TransactionsService transactionsService;
    private final CategoriesService categoriesService;

    @Inject
    public EditTransactionViewModel(TransactionsService transactionsService, CategoriesService categoriesService) {
        this.transactionsService = transactionsService;
        this.categoriesService = categoriesService;
    }

    public TransactionEntryDto getTransactionById(String transactionId) {
        try {
            return transactionsService.getTransactionById(transactionId);
        } catch (Exception e) {
            Log.e("TransactionDetailsViewModel.getTransactionById", "getTransactionById: ", e);
        }

        return null;
    }

    public CategoryDto[] getCategories() {
        List<CategoryDto> categories = categoriesService.getAllCategories();
        return categories.toArray(new CategoryDto[0]);
    }

    public String[] getBanks() {
        return Arrays.stream(BankNames.values()).map(BankNames::toString).toArray(String[]::new);
    }

    public String[] getTransactionTypes() {
        return Arrays.stream(TransactionTypes.values()).map(TransactionTypes::toString).toArray(String[]::new);
    }
}
