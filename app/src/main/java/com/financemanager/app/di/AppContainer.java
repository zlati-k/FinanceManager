package com.financemanager.app.di;

import android.app.Application;

import com.financemanager.app.data.local.AppDatabase;
import com.financemanager.app.data.repository.FinanceRepository;
import com.financemanager.app.data.repository.RoomFinanceRepository;
import com.financemanager.app.ui.ViewModelFactory;

public class AppContainer {

    public final FinanceRepository repository;

    public AppContainer(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        repository = new RoomFinanceRepository(database);
    }

    public ViewModelFactory viewModelFactory() {
        return new ViewModelFactory(repository);
    }
}
