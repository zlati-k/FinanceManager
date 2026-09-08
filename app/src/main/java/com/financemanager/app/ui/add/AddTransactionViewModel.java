package com.financemanager.app.ui.add;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.financemanager.app.data.model.TransactionType;
import com.financemanager.app.data.repository.FinanceRepository;

public class AddTransactionViewModel extends ViewModel {

    private final FinanceRepository repository;
    private final MutableLiveData<Boolean> saved = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public AddTransactionViewModel(FinanceRepository repository) {
        this.repository = repository;
    }

    public LiveData<Boolean> getSaved() {
        return saved;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void save(String amountText, TransactionType type, String note) {
        if (amountText == null || amountText.trim().isEmpty()) {
            errorMessage.setValue("Въведете сума");
            return;
        }
        double amount;
        try {
            amount = Double.parseDouble(amountText.trim().replace(',', '.'));
        } catch (NumberFormatException exception) {
            errorMessage.setValue("Сумата не е валидно число");
            return;
        }
        if (amount <= 0) {
            errorMessage.setValue("Сумата трябва да е по-голяма от 0");
            return;
        }
        String safeNote = note == null ? "" : note.trim();
        repository.insertTransaction(amount, type, safeNote, System.currentTimeMillis());
        saved.setValue(true);
    }

    public void consumeSaved() {
        saved.setValue(false);
    }
}
