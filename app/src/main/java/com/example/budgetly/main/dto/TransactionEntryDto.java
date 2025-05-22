package com.example.budgetly.main.dto;

import com.example.budgetly.main.entities.TransactionEntity;
import com.example.budgetly.main.enums.BankNames;
import com.example.budgetly.main.enums.TransactionTypes;
import com.example.budgetly.main.utils.DateUtils;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntryDto {

    public TransactionEntryDto(TransactionEntity transactionEntity) {
        setRecipient(transactionEntity.getTransactionRecipient());
        setCost(transactionEntity.getCost());
        setDate(DateUtils.convertUnixToLocalDateTime(transactionEntity.getTransactionDate()));
        setTransactionType(transactionEntity.getTransactionType());
        setBank(transactionEntity.getBank());

        String category = "Groceries";
        setCategory(category);
    }

    private String recipient;
    private Double cost;
    private String category;
    private LocalDateTime date;
    private BankNames bank;
    private TransactionTypes transactionType;
}
