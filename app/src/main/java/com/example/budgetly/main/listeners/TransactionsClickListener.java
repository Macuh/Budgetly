package com.example.budgetly.main.listeners;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.budgetly.main.dto.TransactionEntryDto;
import com.example.budgetly.ui.dashboard.TransactionsFragmentDirections;

import java.util.List;

public class TransactionsClickListener implements View.OnClickListener {
    private final List<TransactionEntryDto> transactions;

    public TransactionsClickListener(List<TransactionEntryDto> transactions) {
        this.transactions = transactions;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        TransactionEntryDto clicked = transactions.get(position);

        TransactionsFragmentDirections.ActionNavigationExpensesToTransactionDetails action =
                TransactionsFragmentDirections.actionNavigationExpensesToTransactionDetails(String.valueOf(clicked.getId()));

        NavController navController = Navigation.findNavController(v);
        navController.navigate(action);
    }
}

