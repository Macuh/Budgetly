package com.example.budgetly.main.listeners;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.example.budgetly.R;
import com.example.budgetly.main.dto.TransactionEntryDto;
import com.example.budgetly.main.entities.TransactionEntity;
import com.example.budgetly.main.repositories.TransactionRepository;
import com.example.budgetly.main.services.NotificationService;
import com.example.budgetly.main.services.converters.BankingAppNotificationConverter;
import com.example.budgetly.main.services.converters.HypeNotificationConverter;
import com.example.budgetly.main.services.converters.TradeRepublicNotificationConverter;
import com.example.budgetly.main.utils.DateUtils;
import com.example.budgetly.main.utils.TransactionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationListener extends NotificationListenerService {

    @Inject
    public TransactionRepository transactionRepository;

    private void showDailyExpensesNotification() {
        List<TransactionEntity> dailyTransactions = transactionRepository.getDailyExpenses(DateUtils.convertLocalDateTimeToNumericYearMonthDay(LocalDateTime.now()));
        List<TransactionEntryDto> dailyTransactionsDto = dailyTransactions.stream().map(TransactionEntryDto::new).collect(Collectors.toList());

        float dailyExpenses = TransactionUtils.sumTransactions(dailyTransactionsDto);

        BigDecimal formattedDailyExpenses = new BigDecimal(dailyExpenses);
        new NotificationService(this.getApplicationContext(), null).postNotification("Riepilogo spese", String.format("Oggi hai speso %s", formattedDailyExpenses.setScale(2, RoundingMode.CEILING)));
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();

        BankingAppNotificationConverter bankingAppNotificationConverter = null;

        if(packageName.equals(getString(R.string.trade_rep_package)))
            bankingAppNotificationConverter = new TradeRepublicNotificationConverter();
        else if (packageName.equals(getString(R.string.hype_package)))
            bankingAppNotificationConverter = new HypeNotificationConverter();
        else if (packageName.equals(getString(R.string.test_package)))
            bankingAppNotificationConverter = new TradeRepublicNotificationConverter();

        if(bankingAppNotificationConverter == null)
            return;

        TransactionEntity newTransaction = bankingAppNotificationConverter.extractNotificationData(sbn);

        if(newTransaction != null && newTransaction.getCost() != null) {
            transactionRepository.insert(newTransaction);
            showDailyExpensesNotification();
        }
    }
}
