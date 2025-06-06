package com.example.budgetly.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryExpensesDto {
    private Long categoryId;
    private Double expenses;
}
