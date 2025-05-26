package com.example.budgetly.main.services.converters;

import android.service.notification.StatusBarNotification;

import com.example.budgetly.main.entities.TransactionEntity;
import com.example.budgetly.main.enums.BankNames;
import com.example.budgetly.main.enums.TransactionTypes;
import com.example.budgetly.main.utils.DateUtils;

import java.time.LocalDateTime;

public abstract class BankingAppNotificationConverter {
     protected abstract BankNames getBank();
     protected abstract String getRecipient(String title, String text);
     protected abstract Double getCost(String title, String text);
     protected abstract TransactionTypes getTransactionType(String title, String text);

     public TransactionEntity extractNotificationData(StatusBarNotification sbn) {
          String title = sbn.getNotification().extras.getString("android.title");
          String text = sbn.getNotification().extras.getString("android.text");

          if(title == null || text == null)
               return null;

          TransactionEntity transactionEntity = new TransactionEntity();
          transactionEntity.setBank(getBank());
          transactionEntity.setTransactionRecipient(getRecipient(title, text));
          transactionEntity.setCost(getCost(title, text));
          transactionEntity.setTransactionType(getTransactionType(title, text));
          transactionEntity.setTransactionDate(DateUtils.convertLocalDateTimeToUnix(LocalDateTime.now()));
          transactionEntity.setCategory(null);

          return transactionEntity;
     }
}
