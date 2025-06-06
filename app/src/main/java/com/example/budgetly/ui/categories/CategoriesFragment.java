package com.example.budgetly.ui.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetly.databinding.FragmentCategoriesBinding;
import com.example.budgetly.main.listeners.CategoriesMonthListener;
import com.example.budgetly.main.utils.DateUtils;
import com.github.mikephil.charting.charts.PieChart;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CategoriesFragment extends Fragment {

    private FragmentCategoriesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CategoriesViewModel categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);

        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final PieChart categoriesChart = binding.pieChart;
        final RecyclerView categoriesList = binding.itemList;
        final TextView emptyText = binding.emptyText;
        final Spinner monthSelector = binding.monthSelector;

        List<String> allTransactionMonths = categoriesViewModel.getAllTransactionMonths();
        String actualMonth = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));

        if(!allTransactionMonths.contains(actualMonth)) {
            allTransactionMonths = new ArrayList<>(allTransactionMonths);
            allTransactionMonths.add(0, actualMonth);
        }

        List<String> displayableMonths = allTransactionMonths.stream()
                .map(DateUtils::convertNumericYearMonthToDisplayableMonth)
                .collect(Collectors.toList());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, displayableMonths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSelector.setAdapter(adapter);

        monthSelector.setOnItemSelectedListener(new CategoriesMonthListener(getContext(), categoriesChart, emptyText, categoriesList, categoriesViewModel));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}