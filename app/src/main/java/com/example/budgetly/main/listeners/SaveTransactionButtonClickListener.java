package com.example.budgetly.main.listeners;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.budgetly.main.dto.TransactionEntryDto;
import com.example.budgetly.main.enums.BankNames;
import com.example.budgetly.main.enums.TransactionTypes;
import com.example.budgetly.main.services.TransactionsService;
import com.example.budgetly.main.utils.DateUtils;
import com.example.budgetly.main.utils.DialogsUtils;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;

public class SaveTransactionButtonClickListener implements View.OnClickListener {
    private final TransactionsService transactionsService;
    private final TransactionEntryDto transactionEntryDto;
    private final EditText recipientEditText;
    private final EditText costEditText;
    private final AutoCompleteTextView bankAutoComplete;
    private final AutoCompleteTextView categoryAutoComplete;
    private final AutoCompleteTextView transactionTypeAutoComplete;
    private final EditText dateEditText;

    @AssistedInject
    public SaveTransactionButtonClickListener(
            TransactionsService transactionsService,
            @Assisted TransactionEntryDto transactionEntryDto,
            @Assisted("recipient") EditText recipientEditText,
            @Assisted("cost") EditText costEditText,
            @Assisted("bank") AutoCompleteTextView bankAutoComplete,
            @Assisted("category") AutoCompleteTextView categoryAutoComplete,
            @Assisted("transactionType") AutoCompleteTextView transactionTypeAutoComplete,
            @Assisted("date") EditText dateEditText
    ) {
        this.transactionsService = transactionsService;
        this.transactionEntryDto = transactionEntryDto;
        this.recipientEditText = recipientEditText;
        this.costEditText = costEditText;
        this.bankAutoComplete = bankAutoComplete;
        this.categoryAutoComplete = categoryAutoComplete;
        this.transactionTypeAutoComplete = transactionTypeAutoComplete;
        this.dateEditText = dateEditText;
    }

    private void updateTransactionFields() {
        transactionEntryDto.setRecipient(recipientEditText.getText().toString());
        transactionEntryDto.setCost(Double.valueOf(costEditText.getText().toString()));
        transactionEntryDto.setCategory(null); // TODO: Implement category
        transactionEntryDto.setBank(BankNames.fromString(bankAutoComplete.getText().toString()));
        transactionEntryDto.setTransactionType(TransactionTypes.fromString(transactionTypeAutoComplete.getText().toString()));
        transactionEntryDto.setDate(DateUtils.convertDisplayableDateToLocalDateTime(dateEditText.getText().toString()));
    }

    private boolean saveTransaction() {
        try {
            updateTransactionFields();
            return transactionsService.saveTransaction(transactionEntryDto);
        } catch (Exception e) {
            return false;
        }
    }

    private void dialogPositiveButton(View v, NavController navController) {
        if(saveTransaction()) {
            Toast.makeText(v.getContext(), "Transaction updated", Toast.LENGTH_SHORT).show();
            navController.navigateUp();
        } else {
            Toast.makeText(v.getContext(), "Error during transaction deletion", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        NavController navController = Navigation.findNavController(v);

        DialogsUtils.showAlertDialog(v.getContext(),
                "Confirm Saving",
                "Are you sure you want to save this transaction?",
                "Save",
                (dialog, which) -> dialogPositiveButton(v, navController),
                "Cancel",
                null);
    }

    @AssistedFactory
    public interface Factory {
        SaveTransactionButtonClickListener create(
                TransactionEntryDto transactionEntryDto,
                @Assisted("recipient") EditText recipientEditText,
                @Assisted("cost") EditText costEditText,
                @Assisted("bank") AutoCompleteTextView bankAutoComplete,
                @Assisted("category") AutoCompleteTextView categoryAutoComplete,
                @Assisted("transactionType") AutoCompleteTextView transactionTypeAutoComplete,
                @Assisted("date") EditText dateEditText);
    }
}
