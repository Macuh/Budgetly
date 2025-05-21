package com.example.budgetly.main.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSummaryDto {
    private Double totalCost;
    private List<TransactionEntryDto> transactions;
}
