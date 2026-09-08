package com.financemanager.app.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.financemanager.app.FinanceManagerApp;
import com.financemanager.app.R;
import com.financemanager.app.databinding.FragmentDashboardBinding;
import com.financemanager.app.ui.transactions.TransactionAdapter;
import com.financemanager.app.util.MoneyFormat;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FinanceManagerApp app = (FinanceManagerApp) requireActivity().getApplication();
        DashboardViewModel viewModel = new ViewModelProvider(this, app.getContainer().viewModelFactory())
                .get(DashboardViewModel.class);

        viewModel.getBalance().observe(getViewLifecycleOwner(),
                value -> binding.textBalance.setText(MoneyFormat.formatBgn(value == null ? 0 : value)));
        viewModel.getIncomeTotal().observe(getViewLifecycleOwner(),
                value -> binding.textIncome.setText(MoneyFormat.formatBgn(value == null ? 0 : value)));
        viewModel.getExpenseTotal().observe(getViewLifecycleOwner(),
                value -> binding.textExpense.setText(MoneyFormat.formatBgn(value == null ? 0 : value)));

        TransactionAdapter adapter = new TransactionAdapter();
        binding.recyclerTransactions.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerTransactions.setAdapter(adapter);
        viewModel.getTransactions().observe(getViewLifecycleOwner(), transactions -> {
            int count = transactions == null ? 0 : transactions.size();
            binding.textListTitle.setText(getString(R.string.dashboard_recent_count, count));
            boolean empty = count == 0;
            binding.textEmpty.setVisibility(empty ? View.VISIBLE : View.GONE);
            binding.recyclerTransactions.setVisibility(empty ? View.GONE : View.VISIBLE);
            adapter.submitList(transactions);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
