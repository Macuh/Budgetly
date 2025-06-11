package com.example.budgetly.main.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionWithCategory {
    @Embedded
    public TransactionEntity transaction;

    @Relation(
            parentColumn = "category",
            entityColumn = "categoryId"
    )
    public CategoryEntity category;
}
