package com.example.budgetly.main.listeners;

import android.view.View;
import android.widget.TextView;

import com.example.budgetly.main.utils.DialogsUtils;

public class CalendarDateHourPicker implements View.OnClickListener {
    private final TextView hourTextView;

    public CalendarDateHourPicker(TextView hourTextView) {
        this.hourTextView = hourTextView;
    }

    @Override
    public void onClick(View v) {
        DialogsUtils.showDatePickerDialog(v.getContext(), hourTextView);
    }
}
