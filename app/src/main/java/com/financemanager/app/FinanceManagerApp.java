package com.financemanager.app;

import android.app.Application;

import com.financemanager.app.di.AppContainer;

public class FinanceManagerApp extends Application {

    private AppContainer container;

    @Override
    public void onCreate() {
        super.onCreate();
        container = new AppContainer(this);
    }

    public AppContainer getContainer() {
        return container;
    }
}
