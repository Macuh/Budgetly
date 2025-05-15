package com.example.budgetly.ui.dashboard;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetly.databinding.FragmentTransactionsBinding;

public class TransactionsFragment extends Fragment {

    private FragmentTransactionsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TransactionsViewModelFactory factory = new TransactionsViewModelFactory(requireActivity().getApplication());
        TransactionsViewModel transactionsViewModel = new ViewModelProvider(this, factory).get(TransactionsViewModel.class);

        binding = FragmentTransactionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        transactionsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static class TransactionsViewModelFactory implements ViewModelProvider.Factory {
        private Application application;

        public TransactionsViewModelFactory(Application application) {
            this.application = application;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(TransactionsViewModel.class)) {
                return (T) new TransactionsViewModel(application);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}