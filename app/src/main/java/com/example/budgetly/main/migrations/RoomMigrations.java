package com.example.budgetly.main.migrations;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class RoomMigrations {
    public static final Migration addTransactionTypeAndTransactionDateToTransactions = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase db) {
            db.execSQL("ALTER TABLE transactions ADD COLUMN transaction_type TEXT");
            db.execSQL("ALTER TABLE transactions ADD COLUMN transaction_date INTEGER");
        }
    };
}
