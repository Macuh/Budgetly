package com.example.budgetly.main.listeners;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.budgetly.main.dto.TransactionEntryDto;

import java.util.List;
import java.util.function.Function;

public class TransactionsClickListener implements View.OnClickListener {
    private final List<Object> formattedTransactionList;
    private final Function<TransactionEntryDto, NavDirections> navActionProvider;

    public TransactionsClickListener(
            List<Object> formattedTransactionList,
            Function<TransactionEntryDto, NavDirections> navActionProvider
    ) {
        this.formattedTransactionList = formattedTransactionList;
        this.navActionProvider = navActionProvider;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();

        Object clicked = formattedTransactionList.get(position);

        if (!(clicked instanceof TransactionEntryDto))
            return;

        TransactionEntryDto transactionEntryDto = (TransactionEntryDto) clicked;

        NavDirections action = navActionProvider.apply(transactionEntryDto);

        NavController navController = Navigation.findNavController(v);
        navController.navigate(action);
    }
}
