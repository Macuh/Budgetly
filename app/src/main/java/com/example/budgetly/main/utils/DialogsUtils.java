package com.example.budgetly.main.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DialogsUtils {
    public static void showDatePickerDialog(Context context, TextView textView) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            context,
                            (timeView, selectedHour, selectedMinute) -> {
                                Calendar selectedDateTime = Calendar.getInstance();
                                selectedDateTime.set(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute);

                                SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.displayableDateStringFormat, Locale.getDefault());
                                String formatted = sdf.format(selectedDateTime.getTime());

                                textView.setText(formatted);
                            },
                            hour,
                            minute,
                            true
                    );

                    timePickerDialog.show();
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
    }

    public static void showAlertDialog(Context context, String title, String message, String positiveButtonString, DialogInterface.OnClickListener positiveButtonAction, String negativeButtonString, DialogInterface.OnClickListener negativeButtonAction) {
        new android.app.AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonString, positiveButtonAction)
                .setNegativeButton(negativeButtonString, negativeButtonAction)
                .show();
    }
}
