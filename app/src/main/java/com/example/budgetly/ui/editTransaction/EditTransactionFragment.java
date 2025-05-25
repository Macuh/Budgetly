package com.example.budgetly.ui.editTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.budgetly.databinding.FragmentEditTransactionBinding;


public class EditTransactionFragment extends Fragment {

    public EditTransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentEditTransactionBinding binding = FragmentEditTransactionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}