package com.example.budgetly.main.listeners;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.budgetly.main.dto.TransactionEntryDto;

import java.util.List;

public class TransactionsClickListener implements View.OnClickListener {
    Context context;
    List<TransactionEntryDto> transactions;

    public TransactionsClickListener(Context context, List<TransactionEntryDto> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        TransactionEntryDto clicked = transactions.get(position);
        Toast.makeText(context, "Clicked: " + clicked.getRecipient(), Toast.LENGTH_SHORT).show();
    }
}
