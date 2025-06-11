package com.example.budgetly.main.dto;

import androidx.annotation.NonNull;

import com.example.budgetly.main.entities.CategoryEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    public CategoryDto(CategoryEntity category) {
        setId(category.getCategoryId());
        setCategoryName(category.getCategoryName());
    }

    @NonNull
    @Override
    public String toString() {
        return getCategoryName();
    }

    private Long id;
    private String categoryName;
    private Double totalExpenses;
}
