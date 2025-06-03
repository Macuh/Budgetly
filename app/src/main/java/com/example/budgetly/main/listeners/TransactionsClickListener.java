package com.example.budgetly.main.listeners;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.budgetly.main.dto.TransactionEntryDto;
import com.example.budgetly.ui.dashboard.TransactionsFragmentDirections;

import java.util.List;

public class TransactionsClickListener implements View.OnClickListener {
    private final List<Object> formattedTransactionList;

    public TransactionsClickListener(List<Object> formattedTransactionList) {
        this.formattedTransactionList = formattedTransactionList;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();

        Object clicked = formattedTransactionList.get(position);

        if(!(clicked instanceof TransactionEntryDto))
            return;

        TransactionEntryDto transactionEntryDto = (TransactionEntryDto) clicked;

        TransactionsFragmentDirections.ActionNavigationExpensesToTransactionDetails action =
                TransactionsFragmentDirections.actionNavigationExpensesToTransactionDetails(String.valueOf(transactionEntryDto.getId()));

        NavController navController = Navigation.findNavController(v);
        navController.navigate(action);
    }
}

