package com.example.budgetly.ui.transactionDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.budgetly.R;

public class TransactionDetailsFragment extends Fragment {
    public TransactionDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String transactionId = TransactionDetailsFragmentArgs.fromBundle(requireArguments()).getTransactionId();

        Toast.makeText(this.getContext(), transactionId, Toast.LENGTH_SHORT).show();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction_details, container, false);
    }
}