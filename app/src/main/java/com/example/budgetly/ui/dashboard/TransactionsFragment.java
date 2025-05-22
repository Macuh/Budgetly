package com.example.budgetly.ui.dashboard;

import android.content.Context;
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

import com.example.budgetly.R;
import com.example.budgetly.databinding.FragmentTransactionsBinding;
import com.example.budgetly.main.listeners.TransactionsMonthListener;
import com.example.budgetly.main.utils.DateUtils;
import com.github.mikephil.charting.charts.LineChart;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TransactionsFragment extends Fragment {

    private FragmentTransactionsBinding binding;

    private void setLineChartProperties(LineChart lineChart) {
        Context context = this.getContext();

        if(context != null) {
            lineChart.getXAxis().setTextColor(this.getContext().getColor(R.color.dark_grey));
            lineChart.getAxisLeft().setTextColor(getContext().getColor(R.color.dark_grey));
        }

        lineChart.getAxisLeft().setDrawLabels(true);
        lineChart.getLegend().setEnabled(false);

        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TransactionsViewModel transactionsViewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);

        binding = FragmentTransactionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView totalCostView = binding.totalCost;
        final RecyclerView transactionsList = binding.itemList;
        final TextView emptyListTextView = binding.emptyText;
        final Spinner monthSelector = binding.monthSelector;
        final LineChart lineChart = binding.lineChart;

        setLineChartProperties(lineChart);

        List<String> allTransactionMonths = transactionsViewModel.getAllTransactionMonths();
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

        // Update UI by month selector listener
        monthSelector.setOnItemSelectedListener(new TransactionsMonthListener(getContext(), transactionsViewModel, totalCostView, transactionsList, lineChart, emptyListTextView));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}