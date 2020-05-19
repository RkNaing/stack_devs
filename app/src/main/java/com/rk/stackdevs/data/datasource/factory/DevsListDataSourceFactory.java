package com.rk.stackdevs.data.datasource.factory;


import com.rk.stackdevs.data.datasource.DevsListDataSource;
import com.rk.stackdevs.models.Dev;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

/**
 * Created by Rahul Kumar on 08/05/2020.
 *
 * {@link DevsListDataSource} Factory Class
 **/
public final class DevsListDataSourceFactory extends DataSource.Factory<Integer, Dev> {

    @NonNull
    private final MutableLiveData<DevsListDataSource> dataSourceLiveData;

    public DevsListDataSourceFactory() {
        this.dataSourceLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource<Integer, Dev> create() {
        final DevsListDataSource devsListDataSource = new DevsListDataSource();
        dataSourceLiveData.postValue(devsListDataSource);
        return devsListDataSource;
    }

    @NonNull
    public LiveData<DevsListDataSource> getDataSourceLiveData() {
        return dataSourceLiveData;
    }
}
