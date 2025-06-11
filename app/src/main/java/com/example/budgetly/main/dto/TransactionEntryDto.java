package com.example.budgetly.main.dto;

import com.example.budgetly.main.entities.TransactionEntity;
import com.example.budgetly.main.entities.TransactionWithCategory;
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

    public TransactionEntryDto(TransactionWithCategory transactionWithCategory) {
        setId(transactionWithCategory.getTransaction().getId());
        setRecipient(transactionWithCategory.getTransaction().getTransactionRecipient());
        setCost(transactionWithCategory.getTransaction().getCost());
        setDate(DateUtils.convertUnixToLocalDateTime(transactionWithCategory.getTransaction().getTransactionDate()));
        setTransactionType(transactionWithCategory.getTransaction().getTransactionType());
        setBank(transactionWithCategory.getTransaction().getBank());
        setCategory(transactionWithCategory.getCategory().getCategoryName());
    }

    private Long id;
    private String recipient;
    private Double cost;
    private String category;
    private LocalDateTime date;
    private BankNames bank;
    private TransactionTypes transactionType;
}
