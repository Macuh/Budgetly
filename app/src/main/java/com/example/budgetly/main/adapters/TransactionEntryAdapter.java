package com.example.budgetly.main.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetly.R;
import com.example.budgetly.main.dto.TransactionEntryDto;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import lombok.NonNull;

public class TransactionEntryAdapter extends RecyclerView.Adapter<TransactionEntryAdapter.EntryViewHolder> {
    private final List<TransactionEntryDto> entries;
    private final View.OnClickListener listener;

    public TransactionEntryAdapter(List<TransactionEntryDto> entries, View.OnClickListener listener) {
        this.entries = entries;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_entry_layout, parent, false);

        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
        TransactionEntryDto entry = entries.get(position);

        String formattedCost = entry.getCost() != null ? currencyFormatter.format(entry.getCost()) : "";

        holder.recipientText.setText(entry.getRecipient());
        holder.costText.setText(formattedCost);
        holder.categoryText.setText(entry.getCategory());
        holder.dateText.setText(entry.getDate());
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public static class EntryViewHolder extends RecyclerView.ViewHolder {
        TextView recipientText, costText, dateText, categoryText;

        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);
            recipientText = itemView.findViewById(R.id.recipientText);
            costText = itemView.findViewById(R.id.costText);
            categoryText = itemView.findViewById(R.id.categoryText);
            dateText = itemView.findViewById(R.id.dateText);
        }
    }
}

