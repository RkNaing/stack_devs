package com.rk.stackdevs.ui.activities;

import android.os.Bundle;
import android.view.View;

import com.rk.stackdevs.R;
import com.rk.stackdevs.app.GlideApp;
import com.rk.stackdevs.databinding.ActivityDevsListBinding;
import com.rk.stackdevs.models.ListItemInfo;
import com.rk.stackdevs.ui.adapters.DevsListRVAdapter;
import com.rk.stackdevs.ui.viewmodels.DevsListActivityViewModel;
import com.rk.stackdevs.ui.viewmodels.factories.ContextViewModelFactory;
import com.rk.stackdevs.utils.SpacingItemDecoration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

public class DevsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.rk.stackdevs.databinding.ActivityDevsListBinding binding = ActivityDevsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final ContextViewModelFactory contextViewModelFactory = new ContextViewModelFactory(getApplicationContext());
        final DevsListActivityViewModel devsListActivityViewModel = new ViewModelProvider(this, contextViewModelFactory).get(DevsListActivityViewModel.class);

        final SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(this, R.dimen.margin_list_item);
        binding.mainDevsRV.addItemDecoration(spacingItemDecoration);

        final DevsListRVAdapter devsListRVAdapter = new DevsListRVAdapter(GlideApp.with(this));
        binding.mainDevsRV.setAdapter(devsListRVAdapter);

        binding.mainDevsSrl.setColorSchemeColors(ContextCompat.getColor(getApplicationContext(), R.color.secondary));
        binding.mainDevsSrl.setOnRefreshListener(() -> {
            binding.mainDevsSrl.setRefreshing(false);
            devsListActivityViewModel.refreshList();
        });

        devsListActivityViewModel.getInitialDataRequestStatusLiveData().observe(this, dataRequestStatus -> {
            if (dataRequestStatus == null) {
                return;
            }

            if (dataRequestStatus.isSuccess()) {
                binding.mainDevsRV.setVisibility(View.VISIBLE);
                binding.mainListInfoView.clear();
            } else if (dataRequestStatus.isFail()) {
                binding.mainDevsRV.setVisibility(View.GONE);
                @NonNull final ListItemInfo listItemInfo = ListItemInfo.from(dataRequestStatus);
                binding.mainListInfoView.showMessage(listItemInfo.getErrorMessage(), listItemInfo.getErrorIcon());
            } else if (dataRequestStatus.isLoading()) {
                binding.mainDevsRV.setVisibility(View.GONE);
                binding.mainListInfoView.showLoading(R.string.lbl_loading);
            }

        });

        devsListActivityViewModel.getDataRequestStatusLiveData().observe(this, devsListRVAdapter::setStatus);

        devsListActivityViewModel.getDevsPagedListLiveData().observe(this, devsListRVAdapter::submitList);

    }

}
