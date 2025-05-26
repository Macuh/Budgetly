package com.example.budgetly.ui.editTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetly.R;
import com.example.budgetly.databinding.FragmentEditTransactionBinding;
import com.example.budgetly.main.dto.TransactionEntryDto;
import com.example.budgetly.main.listeners.CalendarDateHourPicker;
import com.example.budgetly.main.listeners.SaveTransactionButtonClickListener;
import com.example.budgetly.main.utils.DateUtils;
import com.google.android.material.button.MaterialButton;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EditTransactionFragment extends Fragment {
    @Inject
    SaveTransactionButtonClickListener.Factory saveTransactionListenerFactory;

    private String convertDoubleNumberToString(Double number) {
        return String.format("%s", BigDecimal.valueOf(number).setScale(2, RoundingMode.CEILING));
    }

    private void setupTextFields(EditTransactionViewModel editTransactionViewModel,
                                 TransactionEntryDto transactionEntryDto,
                                 EditText recipientEditText,
                                 EditText costEditText,
                                 AutoCompleteTextView bankAutoComplete,
                                 AutoCompleteTextView categoryAutoComplete,
                                 AutoCompleteTextView transactionTypeAutoComplete,
                                 EditText dateEditText
    ) {
        recipientEditText.setText(transactionEntryDto.getRecipient());

        costEditText.setText(convertDoubleNumberToString(transactionEntryDto.getCost()));

        bankAutoComplete.setAdapter(new ArrayAdapter<>(requireContext(), R.layout.dark_dropdown, editTransactionViewModel.getBanks()));
        bankAutoComplete.setOnClickListener(v -> bankAutoComplete.showDropDown());
        bankAutoComplete.setText(transactionEntryDto.getBank().toString(), false);

        transactionTypeAutoComplete.setAdapter(new ArrayAdapter<>(requireContext(), R.layout.dark_dropdown, editTransactionViewModel.getTransactionTypes()));
        transactionTypeAutoComplete.setOnClickListener(v -> transactionTypeAutoComplete.showDropDown());
        transactionTypeAutoComplete.setText(transactionEntryDto.getTransactionType().toString(), false);

        dateEditText.setText(DateUtils.convertLocalDateTimeToDisplayableDate(transactionEntryDto.getDate()));
        dateEditText.setOnClickListener(new CalendarDateHourPicker(dateEditText));
    }

    public EditTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EditTransactionViewModel editTransactionViewModel = new ViewModelProvider(this).get(EditTransactionViewModel.class);
        String transactionId = EditTransactionFragmentArgs.fromBundle(requireArguments()).getTransactionId();

        TransactionEntryDto transactionEntryDto = editTransactionViewModel.getTransactionById(transactionId);
        FragmentEditTransactionBinding binding = FragmentEditTransactionBinding.inflate(inflater, container, false);

        EditText recipientEditText = binding.recipientEditText;
        EditText costEditText = binding.costEditText;
        AutoCompleteTextView bankAutoComplete = binding.bankAutoComplete;
        AutoCompleteTextView categoryAutoComplete = binding.categoryAutoComplete; // TODO: Implement categories
        AutoCompleteTextView transactionTypeAutoComplete = binding.transactionTypeAutoComplete;
        EditText dateEditText = binding.dateEditText;

        setupTextFields(editTransactionViewModel, transactionEntryDto, recipientEditText, costEditText, bankAutoComplete, categoryAutoComplete, transactionTypeAutoComplete, dateEditText);

        MaterialButton materialButton = binding.saveButton;
        materialButton.setOnClickListener(saveTransactionListenerFactory.create(transactionEntryDto, recipientEditText, costEditText, bankAutoComplete, categoryAutoComplete, transactionTypeAutoComplete, dateEditText));

        return binding.getRoot();
    }
}