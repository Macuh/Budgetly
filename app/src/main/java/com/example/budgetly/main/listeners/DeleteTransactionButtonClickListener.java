package com.example.budgetly.main.listeners;

import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.budgetly.main.repositories.TransactionRepository;
import com.example.budgetly.main.utils.DialogsUtils;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;

public class DeleteTransactionButtonClickListener implements View.OnClickListener {
    private final TransactionRepository transactionRepository;
    private final String transactionId;

    private boolean deleteTransaction(String transactionId) {
        try {
            return transactionRepository.deleteById(Long.valueOf(transactionId));
        } catch (Exception e) {
            return false;
        }
    }

    private void dialogPositiveButton(View v, NavController navController) {
        if(deleteTransaction(transactionId)) {
            Toast.makeText(v.getContext(), "Transaction deleted", Toast.LENGTH_SHORT).show();
            navController.navigateUp();
        } else {
            Toast.makeText(v.getContext(), "Error during transaction deletion", Toast.LENGTH_SHORT).show();
        }
    }

    @AssistedInject
    public DeleteTransactionButtonClickListener(
            TransactionRepository transactionRepository,
            @Assisted String transactionId
    ) {
        this.transactionRepository = transactionRepository;
        this.transactionId = transactionId;
    }

    @Override
    public void onClick(View v) {
        NavController navController = Navigation.findNavController(v);

        DialogsUtils.showAlertDialog(v.getContext(),
                "Confirm Deletion",
                "Are you sure you want to delete this transaction?",
                "Delete",
                (dialog, which) -> dialogPositiveButton(v, navController),
                "Cancel",
                null);
    }

    @AssistedFactory
    public interface Factory {
        DeleteTransactionButtonClickListener create(String transactionId);
    }
}
