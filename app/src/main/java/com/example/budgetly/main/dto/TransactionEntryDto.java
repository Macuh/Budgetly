package com.example.budgetly.main.dto;

import com.example.budgetly.main.entities.TransactionEntity;
import com.example.budgetly.main.enums.BankNames;
import com.example.budgetly.main.enums.TransactionTypes;
import com.example.budgetly.main.utils.DateUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntryDto implements Serializable {

    public TransactionEntryDto(TransactionEntity transactionEntity) {
        setId(transactionEntity.getId());
        setRecipient(transactionEntity.getTransactionRecipient());
        setCost(transactionEntity.getCost());
        setDate(DateUtils.convertUnixToLocalDateTime(transactionEntity.getTransactionDate()));
        setTransactionType(transactionEntity.getTransactionType());
        setBank(transactionEntity.getBank());

        String category = "No Category";
        setCategory(category);
    }

    private Long id;
    private String recipient;
    private Double cost;
    private String category;
    private LocalDateTime date;
    private BankNames bank;
    private TransactionTypes transactionType;
}
