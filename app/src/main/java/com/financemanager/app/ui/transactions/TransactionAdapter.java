package com.financemanager.app.ui.transactions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.financemanager.app.R;
import com.financemanager.app.data.model.Transaction;
import com.financemanager.app.data.model.TransactionType;
import com.financemanager.app.databinding.ItemTransactionBinding;
import com.financemanager.app.util.MoneyFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private final List<Transaction> items = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.forLanguageTag("bg-BG"));

    public void submitList(List<Transaction> transactions) {
        items.clear();
        if (transactions != null) {
            items.addAll(transactions);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTransactionBinding binding = ItemTransactionBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new TransactionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {
        private final ItemTransactionBinding binding;

        TransactionViewHolder(ItemTransactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Transaction transaction) {
            boolean income = transaction.getType() == TransactionType.INCOME;
            String sign = income ? "+" : "-";
            binding.textType.setText(income ? R.string.type_income : R.string.type_expense);
            String note = transaction.getNote();
            if (note == null || note.isEmpty()) {
                binding.textNote.setVisibility(View.GONE);
            } else {
                binding.textNote.setVisibility(View.VISIBLE);
                binding.textNote.setText(note);
            }
            binding.textDate.setText(dateFormat.format(new Date(transaction.getDateMillis())));
            binding.textAmount.setText(sign + " " + MoneyFormat.formatBgn(transaction.getAmount()));
            int color = ContextCompat.getColor(
                    binding.getRoot().getContext(),
                    income ? R.color.income : R.color.expense
            );
            binding.textAmount.setTextColor(color);
        }
    }
}
