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
import com.example.budgetly.main.utils.TransactionUtils;
import com.example.budgetly.ui.dashboard.TransactionsFragmentDirections;
import com.example.budgetly.ui.dashboard.TransactionsViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        List<Object> formattedTransactionList = TransactionUtils.createListOfTransactionsDividedByDay(data);

        transactionsList.setLayoutManager(new LinearLayoutManager(context));

        TransactionsClickListener transactionsClickListener = new TransactionsClickListener(
                formattedTransactionList,
                transaction -> TransactionsFragmentDirections.actionNavigationExpensesToTransactionDetails(String.valueOf(transaction.getId())));

        TransactionEntryAdapter transactionEntryAdapter = new TransactionEntryAdapter(formattedTransactionList, transactionsClickListener);
        transactionsList.setAdapter(transactionEntryAdapter);

        // Show info message if data is empty
        if(data.isEmpty()) {
            transactionsList.setVisibility(GONE);
            emptyListTextView.setVisibility(VISIBLE);
        } else {
            transactionsList.setVisibility(VISIBLE);
            emptyListTextView.setVisibility(GONE);
        }
    }

    private ArrayList<Entry> getBudgetLimitLineEntries() {
        int firstDayOfMonth = DateUtils.getFirstMonthsDay();
        int daysInMonth = YearMonth.now().lengthOfMonth();

        ArrayList<Entry> budgetLimitLineEntries = new ArrayList<>();

        float dailyBudget = 1000.0f / daysInMonth;
        float currentExpectedBudget = 0.0f;

        for(int i = firstDayOfMonth; i <= daysInMonth; i++) {
            currentExpectedBudget += dailyBudget;
            budgetLimitLineEntries.add(new Entry(i, currentExpectedBudget));
        }

        return  budgetLimitLineEntries;
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

            actualSum = actualSum + TransactionUtils.sumTransactions(dailyTransaction);
            summedCostsByDay.add(new Entry(i, actualSum));
        }

        return summedCostsByDay;
    }

    private void updateLineChartData(TransactionSummaryDto transactionSummaryDto, LineChart lineChart) {
        List<TransactionEntryDto> transactions = transactionSummaryDto.getTransactions();
        Map<Integer, List<TransactionEntryDto>> transactionsGroupedByDay = TransactionUtils.groupTransactionsByDay(transactions);

        ArrayList<Entry> budgetLimitLineEntries = getBudgetLimitLineEntries();
        ArrayList<Entry> summedCostsByDay = getDailyChartEntries(transactionsGroupedByDay);

        LineData lineData = new LineData();

        LineDataSet lineDataSet = new LineDataSet(summedCostsByDay, "data");
        lineDataSet.setColor(context.getColor(R.color.dark_green));
        lineDataSet.setLineWidth(5);

        lineDataSet.setValueTextColor(context.getColor(R.color.dark_grey));

        lineData.addDataSet(lineDataSet);

        LineDataSet budgetDataset = new LineDataSet(budgetLimitLineEntries, "Budget Limit");

        budgetDataset.setColor(context.getColor(R.color.light_red));
        budgetDataset.setLineWidth(3f);
        budgetDataset.enableDashedLine(10f, 10f, 0f);
        budgetDataset.setDrawValues(false);
        budgetDataset.setDrawCircles(false);
        budgetDataset.setDrawFilled(false);

        lineData.addDataSet(budgetDataset);

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
