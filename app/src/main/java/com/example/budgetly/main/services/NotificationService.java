package com.example.budgetly.main.services;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;

import com.example.budgetly.R;

import javax.inject.Inject;

public class NotificationService {

    private final Context context;
    private final Activity activity;

    @Inject
    public NotificationService(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    private void createStandardNotificationChannel(NotificationManager notificationManager) {
        NotificationChannel channel = new NotificationChannel(
                context.getString(R.string.notification_channel_id),
                context.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
        );

        channel.setDescription("Standard Channel");
        notificationManager.createNotificationChannel(channel);
    }

    private NotificationManager getNotificationManager() {
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        NotificationChannel notificationChannel = notificationManager.getNotificationChannel(context.getString(R.string.notification_channel_id));

        if(notificationChannel == null)
            createStandardNotificationChannel(notificationManager);

        return notificationManager;
    }

    public void postNotification(String title, String message) {
        AndroidPermissionService.askForPermission(context, activity, Manifest.permission.POST_NOTIFICATIONS);

        NotificationManager notificationManager = getNotificationManager();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(1, builder.build());
    }
}
