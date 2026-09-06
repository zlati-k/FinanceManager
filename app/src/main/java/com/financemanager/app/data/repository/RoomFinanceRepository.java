package com.financemanager.app.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.financemanager.app.data.local.AppDatabase;
import com.financemanager.app.data.local.entity.TransactionEntity;
import com.financemanager.app.data.model.Transaction;
import com.financemanager.app.data.model.TransactionType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RoomFinanceRepository implements FinanceRepository {

    private final AppDatabase database;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public RoomFinanceRepository(AppDatabase database) {
        this.database = database;
    }

    @Override
    public LiveData<List<Transaction>> getTransactions() {
        return Transformations.map(database.transactionDao().observeAll(), RoomFinanceRepository::mapTransactions);
    }

    @Override
    public void insertTransaction(double amount,
                                  TransactionType type,
                                  String note,
                                  long dateMillis) {
        String safeNote = note == null ? "" : note;
        executor.execute(() -> {
            TransactionEntity entity = new TransactionEntity();
            entity.amount = amount;
            entity.type = type.name();
            entity.note = safeNote;
            entity.dateMillis = dateMillis;
            database.transactionDao().insert(entity);
        });
    }

    @Override
    public LiveData<Double> getBalance() {
        return Transformations.map(getTransactions(), transactions -> {
            double[] totals = totalsOf(transactions);
            return totals[0] - totals[1];
        });
    }

    @Override
    public LiveData<Double> getIncomeTotal() {
        return Transformations.map(getTransactions(), transactions -> totalsOf(transactions)[0]);
    }

    @Override
    public LiveData<Double> getExpenseTotal() {
        return Transformations.map(getTransactions(), transactions -> totalsOf(transactions)[1]);
    }

    private static List<Transaction> mapTransactions(List<TransactionEntity> rows) {
        List<Transaction> mapped = new ArrayList<>();
        if (rows == null) {
            return mapped;
        }
        for (TransactionEntity row : rows) {
            TransactionType type = TransactionType.EXPENSE;
            if (row.type != null) {
                type = TransactionType.valueOf(row.type);
            }
            mapped.add(new Transaction(row.id, row.amount, type, row.note, row.dateMillis));
        }
        return mapped;
    }

    private static double[] totalsOf(List<Transaction> transactions) {
        double income = 0;
        double expense = 0;
        if (transactions != null) {
            for (Transaction transaction : transactions) {
                if (transaction.getType() == TransactionType.INCOME) {
                    income += transaction.getAmount();
                } else {
                    expense += transaction.getAmount();
                }
            }
        }
        return new double[]{income, expense};
    }
}
