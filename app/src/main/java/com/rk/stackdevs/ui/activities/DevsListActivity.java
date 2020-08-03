package com.rk.stackdevs.ui.activities;

import android.content.Context;
import android.os.Bundle;

import com.rk.stackdevs.R;
import com.rk.stackdevs.app.GlideApp;
import com.rk.stackdevs.databinding.ActivityDevsListBinding;
import com.rk.stackdevs.models.Dev;
import com.rk.stackdevs.ui.adapters.DevsListRVAdapter;
import com.rk.stackdevs.ui.components.net_paging.NetPagingCallbacks;
import com.rk.stackdevs.ui.viewmodels.DevsListActivityViewModel;
import com.rk.stackdevs.utils.SpacingItemDecoration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class DevsListActivity extends AppCompatActivity implements NetPagingCallbacks<Dev, DevsListRVAdapter> {

    private ActivityDevsListBinding binding;

    private DevsListRVAdapter devsListRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDevsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final DevsListActivityViewModel devsListActivityViewModel = new ViewModelProvider(this).get(DevsListActivityViewModel.class);

        final SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(this, R.dimen.margin_list_item);
        binding.mainDevsRV.addItemDecoration(spacingItemDecoration);

        devsListRVAdapter = new DevsListRVAdapter(GlideApp.with(this));
        binding.mainDevsRV.setAdapter(devsListRVAdapter);

        binding.mainDevsSrl.setColorSchemeColors(ContextCompat.getColor(getApplicationContext(), R.color.secondary));

        devsListActivityViewModel.init(this);
        devsListRVAdapter.setNewInstance(devsListActivityViewModel.getLoadedDataList());
        devsListActivityViewModel.refreshList(false);

    }

    @Nullable
    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @NonNull
    @Override
    public Lifecycle lifecycle() {
        return getLifecycle();
    }

    @NonNull
    @Override
    public RecyclerView getRecyclerView() {
        return binding.mainDevsRV;
    }

    @NonNull
    @Override
    public DevsListRVAdapter getRecyclerAdapter() {
        return devsListRVAdapter;
    }

    @NonNull
    @Override
    public SwipeRefreshLayout getSwipeRefresh() {
        return binding.mainDevsSrl;
    }

    @Override
    public void onSwipeRefreshed() {

    }
}
