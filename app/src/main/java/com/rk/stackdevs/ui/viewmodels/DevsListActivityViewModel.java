package com.rk.stackdevs.ui.viewmodels;

import android.content.Context;

import com.rk.stackdevs.data.DataRequestStatus;
import com.rk.stackdevs.data.datasource.DevsListDataSource;
import com.rk.stackdevs.data.datasource.factory.DevsListDataSourceFactory;
import com.rk.stackdevs.models.Dev;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * Created by Rahul Kumar on 08/05/2020.
 **/
public final class DevsListActivityViewModel extends ContextViewModel {

    private static final int DEVS_LIST_PAGE_SIZE = 20;

    @NonNull
    private final LiveData<DataRequestStatus> dataRequestStatusLiveData;

    @NonNull
    private final LiveData<DataRequestStatus> initialDataRequestStatusLiveData;

    @NonNull
    private final LiveData<PagedList<Dev>> devsPagedListLiveData;

    @NonNull
    private final DevsListDataSourceFactory devsListDataSourceFactory;

    public DevsListActivityViewModel(@NonNull Context context) {
        super(context);

        devsListDataSourceFactory = new DevsListDataSourceFactory();
        dataRequestStatusLiveData = Transformations.switchMap(
                devsListDataSourceFactory.getDataSourceLiveData(),
                DevsListDataSource::getDataRequestStatus);

        initialDataRequestStatusLiveData = Transformations.switchMap(
                devsListDataSourceFactory.getDataSourceLiveData(),
                DevsListDataSource::getInitialDataRequestStatus);

        final PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(DEVS_LIST_PAGE_SIZE * 2)
                .setPageSize(DEVS_LIST_PAGE_SIZE).build();

        final Executor executor = Executors.newFixedThreadPool(5);
        devsPagedListLiveData = new LivePagedListBuilder<>(devsListDataSourceFactory, pagedListConfig)
                .setFetchExecutor(executor)
                .build();

    }

    @NonNull
    public LiveData<DataRequestStatus> getDataRequestStatusLiveData() {
        return dataRequestStatusLiveData;
    }

    @NonNull
    public LiveData<PagedList<Dev>> getDevsPagedListLiveData() {
        return devsPagedListLiveData;
    }


    @NonNull
    public LiveData<DataRequestStatus> getInitialDataRequestStatusLiveData() {
        return initialDataRequestStatusLiveData;
    }

    public void refreshList() {
        @Nullable final DevsListDataSource devsListDataSource = devsListDataSourceFactory.getDataSourceLiveData().getValue();
        if (devsListDataSource != null) {
            devsListDataSource.invalidate();
        }
    }

}
