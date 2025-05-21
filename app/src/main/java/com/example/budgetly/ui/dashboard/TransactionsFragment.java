package com.example.budgetly.ui.dashboard;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetly.databinding.FragmentTransactionsBinding;
import com.example.budgetly.main.adapters.TransactionEntryAdapter;
import com.example.budgetly.main.dto.TransactionEntryDto;
import com.example.budgetly.main.dto.TransactionSummaryDto;
import com.example.budgetly.main.listeners.TransactionsClickListener;
import com.example.budgetly.main.listeners.TransactionsMonthListener;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TransactionsFragment extends Fragment {

    private FragmentTransactionsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TransactionsViewModel transactionsViewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);

        binding = FragmentTransactionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView totalCostView = binding.totalCost;

        final RecyclerView transactionsList = binding.itemList;
        final TextView emptyListTextView = binding.emptyText;

        final Spinner monthSelector = binding.monthSelector;
        List<String> allTransactionMonths = transactionsViewModel.getAllTransactionMonths();

        String actualMonth = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));

        if(!allTransactionMonths.contains(actualMonth)) {
            allTransactionMonths = new ArrayList<>(allTransactionMonths);
            allTransactionMonths.add(0, actualMonth);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, allTransactionMonths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSelector.setAdapter(adapter);

        // Update UI by month selector listener
        monthSelector.setOnItemSelectedListener(new TransactionsMonthListener(getContext(), transactionsViewModel, totalCostView, transactionsList, emptyListTextView));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}