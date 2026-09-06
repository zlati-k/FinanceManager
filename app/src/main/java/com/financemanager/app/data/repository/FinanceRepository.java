package com.financemanager.app.data.repository;

import androidx.lifecycle.LiveData;

import com.financemanager.app.data.model.Transaction;
import com.financemanager.app.data.model.TransactionType;

import java.util.List;

public interface FinanceRepository {

    LiveData<List<Transaction>> getTransactions();

    void insertTransaction(double amount,
                           TransactionType type,
                           String note,
                           long dateMillis);

    LiveData<Double> getBalance();

    LiveData<Double> getIncomeTotal();

    LiveData<Double> getExpenseTotal();
}
