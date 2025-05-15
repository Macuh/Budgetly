package com.example.budgetly.main.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.budgetly.main.DAOs.TransactionDao;
import com.example.budgetly.main.configurations.AppDatabase;
import com.example.budgetly.main.entities.TransactionEntity;
import com.example.budgetly.ui.dashboard.TransactionsViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

public class TransactionRepository {
    private TransactionDao transactionDao;
    private ExecutorService executorService;

    static final ViewModelInitializer<TransactionsViewModel> initializer = new ViewModelInitializer<>(
            TransactionsViewModel.class,
            creationExtras -> {
                Application app = creationExtras.get(APPLICATION_KEY);
                assert app != null;

                return new TransactionsViewModel(app);
            }
    );

    public TransactionRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        transactionDao = database.transactionDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(TransactionEntity transaction) {
        executorService.execute(() -> transactionDao.insert(transaction));
    }

    public LiveData<List<TransactionEntity>> getAllTransactions() {
        return transactionDao.getAllTransactions();
    }
}
