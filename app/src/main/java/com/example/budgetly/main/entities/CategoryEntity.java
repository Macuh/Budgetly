package com.example.budgetly.main.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(tableName = "category")
public class CategoryEntity {
    @PrimaryKey(autoGenerate = true)
    private Long categoryId;

    @ColumnInfo(name = "category_name")
    private String categoryName;
}
