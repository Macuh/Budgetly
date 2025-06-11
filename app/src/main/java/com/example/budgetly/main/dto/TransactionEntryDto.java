package com.example.budgetly.main.dto;

import com.example.budgetly.main.entities.CategoryEntity;
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
    private static final String NO_CATEGORY = "No Category";

    public TransactionEntryDto(TransactionEntity transactionEntity) {
        setId(transactionEntity.getId());
        setRecipient(transactionEntity.getTransactionRecipient());
        setCost(transactionEntity.getCost());
        setDate(DateUtils.convertUnixToLocalDateTime(transactionEntity.getTransactionDate()));
        setTransactionType(transactionEntity.getTransactionType());
        setBank(transactionEntity.getBank());
        setCategory(NO_CATEGORY);
        setCategoryId(null);
    }

    public TransactionEntryDto(TransactionWithCategory transactionWithCategory) {
        setId(transactionWithCategory.getTransaction().getId());
        setRecipient(transactionWithCategory.getTransaction().getTransactionRecipient());
        setCost(transactionWithCategory.getTransaction().getCost());
        setDate(DateUtils.convertUnixToLocalDateTime(transactionWithCategory.getTransaction().getTransactionDate()));
        setTransactionType(transactionWithCategory.getTransaction().getTransactionType());
        setBank(transactionWithCategory.getTransaction().getBank());

        CategoryEntity categoryEntity = transactionWithCategory.getCategory();
        setCategory(categoryEntity != null ? categoryEntity.getCategoryName() : NO_CATEGORY);
        setCategoryId(categoryEntity != null ? categoryEntity.getCategoryId() : null);
    }

    private Long id;
    private String recipient;
    private Double cost;
    private Long categoryId;
    private String category;
    private LocalDateTime date;
    private BankNames bank;
    private TransactionTypes transactionType;
}
