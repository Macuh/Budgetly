package com.example.budgetly.main.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetly.R;
import com.example.budgetly.main.dto.CategoryDto;
import com.example.budgetly.main.listeners.CategoriesClickListener;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CategoryEntryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<CategoryDto> items;
    private final String yearAndMonth;

    public CategoryEntryAdapter(List<CategoryDto> items, String yearAndMonth) {
        this.items = items;
        this.yearAndMonth = yearAndMonth;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_entry_layout, parent, false);
        return new CategoryEntryAdapter.EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CategoryDto categoryDto = items.get(position);
        EntryViewHolder entryHolder = (EntryViewHolder) holder;

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
        String formattedCost = categoryDto.getTotalExpenses() != null ? currencyFormatter.format(categoryDto.getTotalExpenses()) : "";

        entryHolder.categoryText.setText(categoryDto.getCategoryName());
        entryHolder.totalCategoryExpensesText.setText(formattedCost);

        entryHolder.itemView.setOnClickListener(new CategoriesClickListener(categoryDto.getId(), this.yearAndMonth));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class EntryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryText, totalCategoryExpensesText;

        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.categoryText);
            totalCategoryExpensesText = itemView.findViewById(R.id.total_category_expenses);
        }
    }
}
