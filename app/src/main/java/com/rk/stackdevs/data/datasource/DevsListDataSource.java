package com.rk.stackdevs.data.datasource;

import android.util.Log;

import com.rk.stackdevs.data.DataRequestException;
import com.rk.stackdevs.data.DataRequestStatus;
import com.rk.stackdevs.data.rest.NetCallResult;
import com.rk.stackdevs.data.rest.RestClient;
import com.rk.stackdevs.data.rest.RetrofitCallbackHelper;
import com.rk.stackdevs.data.rest.endpoints.DevsEndpoint;
import com.rk.stackdevs.models.Dev;
import com.rk.stackdevs.models.DevsListResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

/**
 * Created by Rahul Kumar on 08/05/2020.
 *
 * DataSource class for fetching list of developers from API
 **/
public final class DevsListDataSource extends PageKeyedDataSource<Integer, Dev> {

    private static final String TAG = "DevsListDataSource";

    @NonNull
    private final MutableLiveData<DataRequestStatus> initialDataRequestStatus;

    @NonNull
    private final MutableLiveData<DataRequestStatus> dataRequestStatus;

    @NonNull
    private final DevsEndpoint devsEndpoint;

    public DevsListDataSource() {
        this.initialDataRequestStatus = new MutableLiveData<>();
        this.dataRequestStatus = new MutableLiveData<>();
        this.devsEndpoint = RestClient.getDevsEndpoint();
    }

    //region PageKeyedDataSource Implementation
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Dev> callback) {
        Log.d(TAG, "DevsListDataSource:loadInitial: called");

        final DataRequestStatus loadingStatus = DataRequestStatus.loading();
        initialDataRequestStatus.postValue(loadingStatus);
        dataRequestStatus.postValue(loadingStatus);

        devsEndpoint.getDevsList(1, params.requestedLoadSize).enqueue(new RetrofitCallbackHelper<DevsListResponse>() {
            @Override
            protected void onSuccess(@NonNull DevsListResponse data, int responseCode) {
                final DataRequestStatus successStatus = DataRequestStatus.success();
                initialDataRequestStatus.postValue(successStatus);
                dataRequestStatus.postValue(successStatus);
                callback.onResult(data.requireDevs(), null, 2);
            }

            @Override
            protected void onFailure(@NonNull Throwable t, int responseCode, @NetCallResult int resultCode) {
                final Runnable retryRunnable = () -> loadInitial(params, callback);
                final DataRequestStatus failedStatus = DataRequestStatus.failed(new DataRequestException(t.getMessage(), resultCode), retryRunnable);
                initialDataRequestStatus.postValue(failedStatus);
                dataRequestStatus.postValue(failedStatus);
            }

            @Override
            protected void onComplete() {
                // do nothing yet
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Dev> callback) {
        Log.d(TAG, "DevsListDataSource:loadBefore: called");
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Dev> callback) {
        Log.d(TAG, "DevsListDataSource:loadAfter: called");

        final DataRequestStatus loadingStatus = DataRequestStatus.loading();
        dataRequestStatus.postValue(loadingStatus);

        devsEndpoint.getDevsList(params.key, params.requestedLoadSize).enqueue(new RetrofitCallbackHelper<DevsListResponse>() {
            @Override
            protected void onSuccess(@NonNull DevsListResponse data, int responseCode) {
                final DataRequestStatus successStatus = DataRequestStatus.success();
                dataRequestStatus.postValue(successStatus);
                final int nextKey = params.key + 1;
                callback.onResult(data.requireDevs(), data.isHasMore() ? nextKey : null);
            }

            @Override
            protected void onFailure(@NonNull Throwable t, int responseCode, @NetCallResult int resultCode) {
                final Runnable retryRunnable = () -> loadAfter(params, callback);
                final DataRequestStatus failedStatus = DataRequestStatus.failed(new DataRequestException(t.getMessage(), resultCode), retryRunnable);
                dataRequestStatus.postValue(failedStatus);
            }

            @Override
            protected void onComplete() {
                // do nothing yet
            }
        });
    }
    //endregion

    /**
     * @return {@link LiveData} of {@link DataRequestStatus} for initial loading of developers
     */
    @NonNull
    public LiveData<DataRequestStatus> getInitialDataRequestStatus() {
        return initialDataRequestStatus;
    }

    /**
     * @return {@link LiveData} of {@link DataRequestStatus} for further of developers
     */
    @NonNull
    public LiveData<DataRequestStatus> getDataRequestStatus() {
        return dataRequestStatus;
    }

}
