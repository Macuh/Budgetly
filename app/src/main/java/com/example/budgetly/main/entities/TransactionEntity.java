package com.example.budgetly.main.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.budgetly.main.enums.BankNames;
import com.example.budgetly.main.enums.TransactionTypes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "transactions",
        foreignKeys = {
                @ForeignKey(entity = CategoryEntity.class,
                        parentColumns = "categoryId",
                        childColumns = "category",
                        onDelete = ForeignKey.CASCADE)
        })
public class TransactionEntity {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "transaction_recipient")
    private String transactionRecipient;

    @ColumnInfo(name = "cost")
    private Double cost;

    @ColumnInfo(name = "bank")
    private BankNames bank;

    @ColumnInfo(name = "category")
    private Long category;

    @ColumnInfo(name = "transaction_type")
    private TransactionTypes transactionType;

    @ColumnInfo(name = "transaction_date")
    private Long transactionDate;
}
