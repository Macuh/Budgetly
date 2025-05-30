package com.example.budgetly.main.listeners;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.example.budgetly.R;
import com.example.budgetly.main.entities.TransactionEntity;
import com.example.budgetly.main.repositories.TransactionRepository;
import com.example.budgetly.main.services.NotificationService;
import com.example.budgetly.main.services.converters.BankingAppNotificationConverter;
import com.example.budgetly.main.services.converters.TradeRepublicNotificationConverter;
import com.example.budgetly.main.utils.DateUtils;

import java.time.LocalDateTime;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationListener extends NotificationListenerService {

    @Inject
    public TransactionRepository transactionRepository;

    @Inject
    public NotificationService notificationService;

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();

        BankingAppNotificationConverter bankingAppNotificationConverter = null;

        if(packageName.equals(getString(R.string.trade_rep_package)))
            bankingAppNotificationConverter = new TradeRepublicNotificationConverter();
        else if (packageName.equals(getString(R.string.test_package)))
            bankingAppNotificationConverter = new TradeRepublicNotificationConverter();

        if(bankingAppNotificationConverter == null)
            return;

        TransactionEntity newTransaction = bankingAppNotificationConverter.extractNotificationData(sbn);

        if(newTransaction != null && newTransaction.getCost() != null) {
            transactionRepository.insert(newTransaction);
            Double dailyExpenses = transactionRepository.getDailyExpenses(DateUtils.convertLocalDateTimeToNumericYearMonthDay(LocalDateTime.now()));
            notificationService.postNotification("Riepilogo spese", String.format("Oggi hai speso %s", dailyExpenses));
        }
    }
}
