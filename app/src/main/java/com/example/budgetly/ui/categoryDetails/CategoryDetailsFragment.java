package com.example.budgetly.ui.categoryDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetly.databinding.FragmentCategoryDetailsBinding;
import com.example.budgetly.main.adapters.TransactionEntryAdapter;
import com.example.budgetly.main.dto.TransactionEntryDto;
import com.example.budgetly.main.listeners.TransactionsClickListener;
import com.example.budgetly.main.utils.TransactionUtils;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CategoryDetailsFragment extends Fragment {
    private FragmentCategoryDetailsBinding binding;

    private void displayTransactionsInfo(List<TransactionEntryDto> data, RecyclerView transactionsList) {
        List<Object> formattedTransactionList = TransactionUtils.createListOfTransactionsDividedByDay(data);

        transactionsList.setLayoutManager(new LinearLayoutManager(getContext()));

        TransactionsClickListener transactionsClickListener = new TransactionsClickListener(
                formattedTransactionList,
                transaction -> CategoryDetailsFragmentDirections.actionCategoryDetailsToTransactionDetails(String.valueOf(transaction.getId())));

        TransactionEntryAdapter transactionEntryAdapter = new TransactionEntryAdapter(formattedTransactionList, transactionsClickListener);
        transactionsList.setAdapter(transactionEntryAdapter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CategoryDetailsViewModel categoryDetailsViewModel = new ViewModelProvider(this).get(CategoryDetailsViewModel.class);

        binding = FragmentCategoryDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String categoryId = CategoryDetailsFragmentArgs.fromBundle(requireArguments()).getCategoryId();
        String yearAndMonth = CategoryDetailsFragmentArgs.fromBundle(requireArguments()).getMonth();

        List<TransactionEntryDto> transactions = categoryDetailsViewModel.retrieveTransactionsByCategoryIdAndMonth(yearAndMonth, categoryId);
        float totalCost = TransactionUtils.sumTransactions(transactions);

        String categoryName = categoryDetailsViewModel.retrieveCategoryName(categoryId);

        TextView categoryNameTextView = binding.categoryTitle;
        categoryNameTextView.setText(categoryName);

        TextView totalCostTextView = binding.monthlyTotal;
        totalCostTextView.setText(TransactionUtils.makePriceDisplayable((double) totalCost));

        RecyclerView transactionListView = binding.transactionList;
        displayTransactionsInfo(transactions, transactionListView);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}