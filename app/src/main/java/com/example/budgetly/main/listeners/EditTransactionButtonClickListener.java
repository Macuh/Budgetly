package com.example.budgetly.main.listeners;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.budgetly.ui.transactionDetails.TransactionDetailsFragmentDirections;

public class EditTransactionButtonClickListener implements View.OnClickListener {

    private final String transactionId;

    public EditTransactionButtonClickListener(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public void onClick(View v) {
        TransactionDetailsFragmentDirections.ActionNavigationTransactionDetailsToEditTransaction action =
                TransactionDetailsFragmentDirections.actionNavigationTransactionDetailsToEditTransaction(transactionId);

        NavController navController = Navigation.findNavController(v);
        navController.navigate(action);
    }
}
