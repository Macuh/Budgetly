package com.example.budgetly.main.listeners;

import android.view.View;

import com.example.budgetly.main.services.NotificationService;

import javax.inject.Inject;

public class ButtonClickListener implements View.OnClickListener {
    private final NotificationService notificationService;

    @Inject
    public ButtonClickListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void onClick(View v) {
        notificationService.postNotification("test recipient", "Spent $21.30 at Just Eat");
    }
}
