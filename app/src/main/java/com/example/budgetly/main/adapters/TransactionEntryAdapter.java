package com.example.budgetly.main.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetly.R;
import com.example.budgetly.main.dto.TransactionEntryDto;
import com.example.budgetly.main.enums.TransactionTypes;
import com.example.budgetly.main.utils.DateUtils;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import lombok.NonNull;

public class TransactionEntryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_DIVIDER = 0;
    private static final int VIEW_TYPE_ENTRY = 1;

    private final List<Object> items;
    private final View.OnClickListener listener;

    public TransactionEntryAdapter(List<Object> items, View.OnClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Integer) {
            return VIEW_TYPE_DIVIDER;
        } else {
            return VIEW_TYPE_ENTRY;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DIVIDER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.day_divisor_layout, parent, false);
            return new DividerViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.transaction_entry_layout, parent, false);
            return new EntryViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_DIVIDER) {
            Integer dayLabel = (Integer) items.get(position);
            ((DividerViewHolder) holder).dividerText.setText(String.valueOf(dayLabel));
        } else {
            TransactionEntryDto entry = (TransactionEntryDto) items.get(position);
            EntryViewHolder entryHolder = (EntryViewHolder) holder;

            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());

            String formattedCost = entry.getCost() != null ? currencyFormatter.format(entry.getCost()) : "";

            entryHolder.recipientText.setText(entry.getRecipient());
            entryHolder.categoryText.setText(entry.getCategory());
            entryHolder.dateText.setText(DateUtils.convertLocalDateTimeToDisplayableDate(entry.getDate()));
            entryHolder.bankText.setText(entry.getBank().getDisplayName());

            entryHolder.costText.setText(formattedCost);
            setCostTextColor(entryHolder.costText, entry.getTransactionType());

            entryHolder.itemView.setTag(position);
            entryHolder.itemView.setOnClickListener(listener);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void setCostTextColor(TextView costText, TransactionTypes transactionType) {
        if (transactionType.equals(TransactionTypes.OUTGOING))
            costText.setTextColor(ContextCompat.getColor(costText.getContext(), R.color.light_red));
        else if (transactionType.equals(TransactionTypes.INGOING))
            costText.setTextColor(ContextCompat.getColor(costText.getContext(), R.color.dark_green));
    }

    public static class EntryViewHolder extends RecyclerView.ViewHolder {
        TextView bankText, recipientText, costText, dateText, categoryText;

        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);
            bankText = itemView.findViewById(R.id.bankText);
            recipientText = itemView.findViewById(R.id.recipientText);
            costText = itemView.findViewById(R.id.costText);
            categoryText = itemView.findViewById(R.id.categoryText);
            dateText = itemView.findViewById(R.id.dateText);
        }
    }

    public static class DividerViewHolder extends RecyclerView.ViewHolder {
        TextView dividerText;

        public DividerViewHolder(@NonNull View itemView) {
            super(itemView);
            dividerText = itemView.findViewById(R.id.dividerText);
        }
    }
}
