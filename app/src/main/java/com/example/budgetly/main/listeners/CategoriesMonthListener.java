package com.example.budgetly.main.listeners;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetly.main.adapters.CategoryEntryAdapter;
import com.example.budgetly.main.dto.CategoryDto;
import com.example.budgetly.main.utils.DateUtils;
import com.example.budgetly.ui.categories.CategoriesViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
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

    private void setupPieChart(PieChart pieChart, List<CategoryDto> categoryExpenses) {
        List<PieEntry> entries = new ArrayList<>();
        float total = 0f;

        for (CategoryDto ce : categoryExpenses) {
            if (ce.getTotalExpenses() != null && ce.getTotalExpenses() > 0) {
                total += ce.getTotalExpenses();
            }
        }

        if (total == 0f) {
            pieChart.clear();
            pieChart.setNoDataText("No expense data available.");
            pieChart.setNoDataTextColor(Color.LTGRAY);
            return;
        }

        float otherTotal = 0f;
        for (CategoryDto ce : categoryExpenses) {
            if (ce.getTotalExpenses() == null || ce.getTotalExpenses() <= 0) continue;

            float value = ce.getTotalExpenses().floatValue();
            float percentage = (value / total) * 100f;

            if (percentage < 5f) {
                otherTotal += value;
            } else {
                entries.add(new PieEntry(value, ce.getCategoryName()));
            }
        }

        if (otherTotal > 0f) {
            entries.add(new PieEntry(otherTotal, "Others"));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expenses by Category");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(8f);

        dataSet.setColors(
                Color.rgb(129, 199, 132),   // Light Green
                Color.rgb(255, 167, 38),    // Amber
                Color.rgb(100, 181, 246),   // Light Blue
                Color.rgb(186, 104, 200),   // Lavender Purple
                Color.rgb(239, 83, 80),     // Soft Red
                Color.rgb(255, 143, 0),     // Deep Orange
                Color.rgb(77, 182, 172),    // Teal
                Color.rgb(255, 213, 79)     // Bright Yellow
        );

        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(14f);
        dataSet.setDrawValues(false);

        PieData pieData = new PieData(dataSet);

        pieChart.setData(pieData);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(30f);
        pieChart.setTransparentCircleRadius(35f);
        pieChart.setTransparentCircleColor(Color.BLACK);
        pieChart.setTransparentCircleAlpha(80);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(14f);

        pieChart.setCenterText("Expenses");
        pieChart.setCenterTextSize(14f);
        pieChart.setCenterTextTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        pieChart.setCenterTextRadiusPercent(80f);
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setHoleColor(Color.BLACK);

        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);

        pieChart.animateY(1400, Easing.EaseInOutQuad);
        pieChart.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        Paint holePaint = pieChart.getPaint(Chart.PAINT_HOLE);
        if (holePaint != null) {
            holePaint.setShadowLayer(8f, 0f, 2f, Color.argb(100, 0, 0, 0));
        }

        pieChart.invalidate();
    }

    private void updateCategoriesDataByMonth(String yearAndMonth) {
        List<CategoryDto> categories = categoriesViewModel.getCategoriesWithExpensesByMonth(yearAndMonth);
        displayCategoriesInfo(categories, yearAndMonth);
        setupPieChart(pieChart, categories);

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
