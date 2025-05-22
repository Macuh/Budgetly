package com.example.budgetly.main.listeners;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetly.R;
import com.example.budgetly.main.adapters.TransactionEntryAdapter;
import com.example.budgetly.main.dto.TransactionEntryDto;
import com.example.budgetly.main.dto.TransactionSummaryDto;
import com.example.budgetly.main.utils.DateUtils;
import com.example.budgetly.ui.dashboard.TransactionsViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionsMonthListener implements AdapterView.OnItemSelectedListener {

    private final Context context;
    private final TransactionsViewModel transactionsViewModel;
    private final TextView totalCostView;
    private final RecyclerView transactionsList;
    private final TextView emptyListTextView;
    private final LineChart lineChart;

    public TransactionsMonthListener(Context context, TransactionsViewModel transactionsViewModel, TextView totalCostView, RecyclerView transactionsList, LineChart lineChart, TextView emptyListTextView) {
        this.context = context;
        this.transactionsViewModel = transactionsViewModel;
        this.totalCostView = totalCostView;
        this.transactionsList = transactionsList;
        this.emptyListTextView = emptyListTextView;
        this.lineChart = lineChart;
    }

    private void displayTotalCostInfo(TransactionSummaryDto transactionSummaryDto, TextView totalCostView) {
        totalCostView.setText(String.format("%s$", BigDecimal.valueOf(transactionSummaryDto.getTotalCost()).setScale(2, RoundingMode.CEILING)));
    }

    private void displayTransactionsInfo(TransactionSummaryDto transactionSummaryDto, RecyclerView transactionsList, TextView emptyListTextView) {
        List<TransactionEntryDto> data = transactionSummaryDto.getTransactions();

        transactionsList.setLayoutManager(new LinearLayoutManager(context));
        transactionsList.setAdapter(new TransactionEntryAdapter(data, new TransactionsClickListener(context, data)));

        // Show info message if data is empty
        if(data.isEmpty()) {
            transactionsList.setVisibility(GONE);
            emptyListTextView.setVisibility(VISIBLE);
        } else {
            transactionsList.setVisibility(VISIBLE);
            emptyListTextView.setVisibility(GONE);
        }
    }

    private Map<Integer, List<TransactionEntryDto>> groupTransactionsByDay(List<TransactionEntryDto> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(transactionEntryDto -> transactionEntryDto.getDate().getDayOfMonth()));
    }

    private ArrayList<Entry> getDailyChartEntries(Map<Integer, List<TransactionEntryDto>> transactionsGroupedByDay) {
        int firstDayOfMonth = DateUtils.getFirstMonthsDay();
        int currentDay = DateUtils.getCurrentDay();

        ArrayList<Entry> summedCostsByDay = new ArrayList<>();

        float actualSum = 0f;

        for(int i = firstDayOfMonth; i <= currentDay; i++) {
            List<TransactionEntryDto> dailyTransaction = transactionsGroupedByDay.get(i);

            if(dailyTransaction == null)
                dailyTransaction = Collections.emptyList();

            actualSum = actualSum + (float) dailyTransaction.stream().mapToDouble(TransactionEntryDto::getCost).sum();
            summedCostsByDay.add(new Entry(i, actualSum));
        }

        return summedCostsByDay;
    }

    private void updateLineChartData(TransactionSummaryDto transactionSummaryDto, LineChart lineChart) {
        List<TransactionEntryDto> transactions = transactionSummaryDto.getTransactions();
        Map<Integer, List<TransactionEntryDto>> transactionsGroupedByDay = groupTransactionsByDay(transactions);
        ArrayList<Entry> summedCostsByDay = getDailyChartEntries(transactionsGroupedByDay);

        LineData lineData = new LineData();

        LineDataSet lineDataSet = new LineDataSet(summedCostsByDay, "data");
        lineDataSet.setColor(context.getColor(R.color.dark_green));
        lineDataSet.setLineWidth(5);

        lineData.addDataSet(lineDataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private void updateTransactionDataByMonth(TransactionsViewModel transactionsViewModel, TextView totalCostView, RecyclerView transactionsList, TextView emptyListTextView, String yearAndMonth, LineChart lineChart) {
        TransactionSummaryDto transactionSummaryDto = transactionsViewModel.getTransactionsSummaryByMonth(yearAndMonth);
        updateLineChartData(transactionSummaryDto, lineChart);
        displayTotalCostInfo(transactionSummaryDto, totalCostView);
        displayTransactionsInfo(transactionSummaryDto, transactionsList, emptyListTextView);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String actualYearAndMonth = DateUtils.convertDisplayableMonthToNumericYearMonth(parent.getItemAtPosition(position).toString());
        updateTransactionDataByMonth(transactionsViewModel, totalCostView, transactionsList, emptyListTextView, actualYearAndMonth, lineChart);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
