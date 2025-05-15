package com.example.budgetly.ui.dashboard;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.budgetly.main.entities.TransactionEntity;
import com.example.budgetly.main.repositories.TransactionRepository;

import java.util.List;

public class TransactionsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final TransactionRepository transactionRepository;

    public TransactionsViewModel(Application application) {
        mText = new MutableLiveData<>();
        transactionRepository = new TransactionRepository(application);
    }

    public LiveData<String> getText() {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setBank("ciolla");
        transactionRepository.insert(transaction);

        LiveData<List<TransactionEntity>> transactions = transactionRepository.getAllTransactions();

        try {
            TransactionEntity transaction1 = transactions.getValue().get(0);
            mText.setValue(transaction1.getBank());

        } catch (Exception e) {
            mText.setValue("Error");
        }

        return mText;
    }
}