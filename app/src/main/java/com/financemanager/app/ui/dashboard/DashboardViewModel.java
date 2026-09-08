package com.financemanager.app.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.financemanager.app.data.model.Transaction;
import com.financemanager.app.data.repository.FinanceRepository;

import java.util.List;

public class DashboardViewModel extends ViewModel {

    private final LiveData<Double> balance;
    private final LiveData<Double> incomeTotal;
    private final LiveData<Double> expenseTotal;
    private final LiveData<List<Transaction>> transactions;

    public DashboardViewModel(FinanceRepository repository) {
        balance = repository.getBalance();
        incomeTotal = repository.getIncomeTotal();
        expenseTotal = repository.getExpenseTotal();
        transactions = repository.getTransactions();
    }

    public LiveData<Double> getBalance() {
        return balance;
    }

    public LiveData<Double> getIncomeTotal() {
        return incomeTotal;
    }

    public LiveData<Double> getExpenseTotal() {
        return expenseTotal;
    }

    public LiveData<List<Transaction>> getTransactions() {
        return transactions;
    }
}
