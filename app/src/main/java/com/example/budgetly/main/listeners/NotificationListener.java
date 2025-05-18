package com.example.budgetly.main.listeners;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationListener extends NotificationListenerService {
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
        String title = sbn.getNotification().extras.getString("android.title");
        String text = sbn.getNotification().extras.getString("android.text");

        Log.d("NotificationIntercept", "From: " + packageName + " | Title: " + title + " | Text: " + text);
    }
}
