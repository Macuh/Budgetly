package com.example.budgetly.main.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

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
    private String bank;

    @ColumnInfo(name = "category")
    private Long category;
}
