package com.financemanager.app.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.financemanager.app.data.repository.FinanceRepository;
import com.financemanager.app.ui.add.AddTransactionViewModel;
import com.financemanager.app.ui.dashboard.DashboardViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final FinanceRepository repository;

    public ViewModelFactory(FinanceRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DashboardViewModel.class)) {
            return (T) new DashboardViewModel(repository);
        }
        if (modelClass.isAssignableFrom(AddTransactionViewModel.class)) {
            return (T) new AddTransactionViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
