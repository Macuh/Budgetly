package com.example.budgetly.main.listeners;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.example.budgetly.R;
import com.example.budgetly.main.entities.TransactionEntity;
import com.example.budgetly.main.repositories.TransactionRepository;
import com.example.budgetly.main.services.converters.BankingAppNotificationConverter;
import com.example.budgetly.main.services.converters.TradeRepublicNotificationConverter;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationListener extends NotificationListenerService {

    @Inject
    public TransactionRepository transactionRepository;

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();

        BankingAppNotificationConverter bankingAppNotificationConverter = null;

        if(packageName.equals(getString(R.string.trade_rep_package)))
            bankingAppNotificationConverter = new TradeRepublicNotificationConverter();

        if(bankingAppNotificationConverter == null)
            return;

        TransactionEntity newTransaction = bankingAppNotificationConverter.extractNotificationData(sbn);
        transactionRepository.insert(newTransaction);
    }
}
