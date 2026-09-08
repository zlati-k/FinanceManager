package com.financemanager.app.ui.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.financemanager.app.FinanceManagerApp;
import com.financemanager.app.R;
import com.financemanager.app.data.model.TransactionType;
import com.financemanager.app.databinding.FragmentAddTransactionBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddTransactionFragment extends Fragment {

    private FragmentAddTransactionBinding binding;
    private AddTransactionViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddTransactionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FinanceManagerApp app = (FinanceManagerApp) requireActivity().getApplication();
        viewModel = new ViewModelProvider(this, app.getContainer().viewModelFactory())
                .get(AddTransactionViewModel.class);

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.consumeSaved();
        viewModel.getSaved().observe(getViewLifecycleOwner(), saved -> {
            if (!Boolean.TRUE.equals(saved)) {
                return;
            }
            viewModel.consumeSaved();
            Toast.makeText(requireContext(), R.string.transaction_saved, Toast.LENGTH_SHORT).show();
            BottomNavigationView bottomNav = requireActivity().findViewById(R.id.bottomNav);
            if (bottomNav != null) {
                bottomNav.setSelectedItemId(R.id.dashboardFragment);
            }
        });

        binding.buttonSave.setOnClickListener(v -> viewModel.save(
                String.valueOf(binding.inputAmount.getText()),
                selectedType(),
                String.valueOf(binding.inputNote.getText())
        ));
    }

    private TransactionType selectedType() {
        if (binding.radioIncome.isChecked()) {
            return TransactionType.INCOME;
        }
        return TransactionType.EXPENSE;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
