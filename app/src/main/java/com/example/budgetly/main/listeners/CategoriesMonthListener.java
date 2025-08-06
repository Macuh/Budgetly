package com.example.budgetly.main.listeners;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetly.main.adapters.CategoryEntryAdapter;
import com.example.budgetly.main.dto.CategoryDto;
import com.example.budgetly.main.utils.DateUtils;
import com.example.budgetly.ui.categories.CategoriesViewModel;
import com.github.mikephil.charting.charts.PieChart;

import java.util.List;

public class CategoriesMonthListener implements AdapterView.OnItemSelectedListener {

    private final Context context;
    private final PieChart pieChart;
    private final TextView emptyText;
    private final RecyclerView categoriesList;
    private final CategoriesViewModel categoriesViewModel;

    public CategoriesMonthListener(Context context, PieChart pieChart, TextView emptyText, RecyclerView categoriesList, CategoriesViewModel categoriesViewModel) {
        this.context = context;
        this.pieChart = pieChart;
        this.emptyText = emptyText;
        this.categoriesList = categoriesList;
        this.categoriesViewModel = categoriesViewModel;
    }

    private void displayCategoriesInfo(List<CategoryDto> items, String yearAndMonth) {
        categoriesList.setLayoutManager(new LinearLayoutManager(context));
        categoriesList.setAdapter(new CategoryEntryAdapter(items, yearAndMonth));
    }

    private void updateCategoriesDataByMonth(String yearAndMonth) {
        List<CategoryDto> categories = categoriesViewModel.getCategoriesWithExpensesByMonth(yearAndMonth);
        displayCategoriesInfo(categories, yearAndMonth);

        // Show info message if data is empty
        if(categories.isEmpty()) {
            categoriesList.setVisibility(GONE);
            emptyText.setVisibility(VISIBLE);
        } else {
            categoriesList.setVisibility(VISIBLE);
            emptyText.setVisibility(GONE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String actualYearAndMonth = DateUtils.convertDisplayableMonthToNumericYearMonth(parent.getItemAtPosition(position).toString());
        updateCategoriesDataByMonth(actualYearAndMonth);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
