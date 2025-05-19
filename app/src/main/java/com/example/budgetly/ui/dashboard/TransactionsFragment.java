package com.example.budgetly.ui.dashboard;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.budgetly.main.listeners.TransactionsClickListener;

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

        final RecyclerView recyclerView = binding.itemList;
        final TextView emptyListTextView = binding.emptyText;

        List<TransactionEntryDto> data = transactionsViewModel.getTransactionsAndUpdateView();

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new TransactionEntryAdapter(data, new TransactionsClickListener(getContext(), data)));

        // Show info message if data is empty
        if(data.isEmpty()) {
            recyclerView.setVisibility(GONE);
            emptyListTextView.setVisibility(VISIBLE);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}