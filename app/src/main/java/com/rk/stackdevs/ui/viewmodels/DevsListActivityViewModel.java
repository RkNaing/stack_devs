package com.rk.stackdevs.ui.viewmodels;

import com.rk.stackdevs.R;
import com.rk.stackdevs.data.rest.RestClient;
import com.rk.stackdevs.models.Dev;
import com.rk.stackdevs.models.DevsListResponse;
import com.rk.stackdevs.ui.adapters.DevsListRVAdapter;
import com.rk.stackdevs.ui.components.net_paging.NetAdapterConfig;
import com.rk.stackdevs.ui.components.net_paging.NetAdapterConfigImpl;
import com.rk.stackdevs.ui.components.net_paging.NetPagingViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;

/**
 * Created by Rahul Kumar on 08/05/2020.
 **/
public final class DevsListActivityViewModel extends NetPagingViewModel<DevsListResponse, Dev, DevsListRVAdapter> {

    private static final int DEVS_LIST_PAGE_SIZE = 20;

    private final NetAdapterConfig netAdapterConfig = new NetAdapterConfigImpl(R.string.msg_empty_devs, R.drawable.img_placeholder_profile);

    public DevsListActivityViewModel() {
        setPageSize(DEVS_LIST_PAGE_SIZE);
    }

    @NonNull
    @Override
    protected List<Dev> onDataReceived(@NonNull DevsListResponse data) {
        return data.requireDevs();
    }

    @NonNull
    @Override
    protected NetAdapterConfig getAdapterConfig() {
        return netAdapterConfig;
    }

    @NonNull
    @Override
    protected Call<DevsListResponse> getRetrofitCall(int currentPage) {
        return RestClient.getDevsEndpoint().getDevsList(currentPage, DEVS_LIST_PAGE_SIZE);
    }
}
