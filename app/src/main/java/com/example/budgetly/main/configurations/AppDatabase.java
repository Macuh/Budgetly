package com.example.budgetly.main.configurations;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.budgetly.main.DAOs.CategoryDao;
import com.example.budgetly.main.DAOs.TransactionDao;
import com.example.budgetly.main.entities.CategoryEntity;
import com.example.budgetly.main.entities.TransactionEntity;

@Database(entities = {TransactionEntity.class, CategoryEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TransactionDao transactionDao();
    public abstract CategoryDao categoryDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "budgetly_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
