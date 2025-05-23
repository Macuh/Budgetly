package com.example.budgetly.ui.transactionDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetly.R;
import com.example.budgetly.databinding.FragmentTransactionDetailsBinding;
import com.example.budgetly.main.dto.TransactionEntryDto;
import com.example.budgetly.main.utils.DateUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TransactionDetailsFragment extends Fragment {
    private String convertDoubleNumberToString(Double number) {
        return String.format("%s$", BigDecimal.valueOf(number).setScale(2, RoundingMode.CEILING));
    }


    public TransactionDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TransactionDetailsViewModel transactionDetailsViewModel = new ViewModelProvider(this).get(TransactionDetailsViewModel.class);
        String transactionId = TransactionDetailsFragmentArgs.fromBundle(requireArguments()).getTransactionId();

        TransactionEntryDto transactionEntryDto = transactionDetailsViewModel.getTransactionById(transactionId);
        List<TransactionEntryDto> recipientTransactions = transactionDetailsViewModel.getAllTransactionByRecipient(DateUtils.convertLocalDateTimeToNumericYearMonth(transactionEntryDto.getDate()), transactionEntryDto.getRecipient());

        Double totalRecipientExpenses = transactionDetailsViewModel.getAllRecipientExpenses(recipientTransactions);
        Double totalRecipientReceived = transactionDetailsViewModel.getAllRecipientRevenues(recipientTransactions);

        // Inflate the layout for this fragment
        com.example.budgetly.databinding.FragmentTransactionDetailsBinding binding = FragmentTransactionDetailsBinding.inflate(inflater, container, false);

        TextView amountTextView = binding.amount;
        amountTextView.setText(convertDoubleNumberToString(transactionEntryDto.getCost()));

        TextView recipientTextView = binding.recipient;
        recipientTextView.setText(transactionEntryDto.getRecipient());

        TextView transactionDateTextView = binding.transactionDate;
        transactionDateTextView.setText(DateUtils.convertLocalDateTimeToDisplayableDate(transactionEntryDto.getDate()));

        TextView categoryTextView = binding.categoryText;
        categoryTextView.setText(transactionEntryDto.getCategory());

        TextView bankTextView = binding.bankText;
        bankTextView.setText(transactionEntryDto.getBank().getDisplayName());

        TextView totalExpensesTextView = binding.totalSentText;
        totalExpensesTextView.setText(convertDoubleNumberToString(totalRecipientExpenses));

        TextView totalReceivedTextView = binding.totalReceivedText;
        totalReceivedTextView.setText(convertDoubleNumberToString(totalRecipientReceived));


        return binding.getRoot();
    }
}