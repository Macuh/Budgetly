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
import com.example.budgetly.ui.dashboard.TransactionsViewModel;

import java.util.List;

public class TransactionsMonthListener implements AdapterView.OnItemSelectedListener {

    private final Context context;
    private final TransactionsViewModel transactionsViewModel;
    private final TextView totalCostView;
    private final RecyclerView transactionsList;
    private final TextView emptyListTextView;

    public TransactionsMonthListener(Context context, TransactionsViewModel transactionsViewModel, TextView totalCostView, RecyclerView transactionsList, TextView emptyListTextView) {
        this.context = context;
        this.transactionsViewModel = transactionsViewModel;
        this.totalCostView = totalCostView;
        this.transactionsList = transactionsList;
        this.emptyListTextView = emptyListTextView;
    }

    private void displayTotalCostInfo(TransactionSummaryDto transactionSummaryDto, TextView totalCostView) {
        totalCostView.setText(String.format("%s%s", context.getString(R.string.month_total_cost_header), transactionSummaryDto.getTotalCost()));
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

    private void updateTransactionDataByMonth(TransactionsViewModel transactionsViewModel, TextView totalCostView, RecyclerView transactionsList, TextView emptyListTextView, String yearAndMonth) {
        TransactionSummaryDto transactionSummaryDto = transactionsViewModel.getTransactionsSummaryByMonth(yearAndMonth);
        displayTotalCostInfo(transactionSummaryDto, totalCostView);
        displayTransactionsInfo(transactionSummaryDto, transactionsList, emptyListTextView);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String actualYearAndMonth = parent.getItemAtPosition(position).toString();
        updateTransactionDataByMonth(transactionsViewModel, totalCostView, transactionsList, emptyListTextView, actualYearAndMonth);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
