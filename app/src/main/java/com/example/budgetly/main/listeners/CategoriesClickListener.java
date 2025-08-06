package com.example.budgetly.main.listeners;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.budgetly.ui.categories.CategoriesFragmentDirections;

public class CategoriesClickListener implements View.OnClickListener {
    private final Long categoryId;
    private final String yearAndMonth;

    public CategoriesClickListener(Long categoryId, String yearAndMonth) {
        this.categoryId = categoryId;
        this.yearAndMonth = yearAndMonth;
    }

    @Override
    public void onClick(View v) {
        CategoriesFragmentDirections.ActionNavigationCategoriesToCategoryDetails action =
                CategoriesFragmentDirections.actionNavigationCategoriesToCategoryDetails(categoryId.toString(), yearAndMonth);

        NavController navController = Navigation.findNavController(v);
        navController.navigate(action);
    }
}
