package com.example.budgetly.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetly.databinding.FragmentNotificationsBinding;
import com.example.budgetly.main.listeners.ButtonClickListener;
import com.example.budgetly.main.listeners.CreateNewCategoryClickListener;
import com.example.budgetly.main.repositories.CategoryRepository;
import com.example.budgetly.main.services.NotificationService;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;

        final Button button = binding.button;
        button.setOnClickListener(new ButtonClickListener(new NotificationService(this.getContext(), this.getActivity())));

        final Button button2 = binding.button2;
        button2.setOnClickListener(new CreateNewCategoryClickListener(new CategoryRepository(this.requireActivity().getApplication())));

        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}