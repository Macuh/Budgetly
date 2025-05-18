package com.example.budgetly.main.services;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AndroidPermissionService {
    public static void askForPermission(Context context, Activity activity, String permissionToBeAsked) {
        if (ContextCompat.checkSelfPermission(context, permissionToBeAsked) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(activity, new String[]{permissionToBeAsked}, 1001);
    }
}
