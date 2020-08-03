package com.rk.stackdevs.ui.components.net_paging;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class NetPagingViewModel<M, E, D extends BaseQuickAdapter<E, ? extends BaseViewHolder>> extends ViewModel implements LifecycleObserver {

    //region Constants
    private static final String TAG = "NetPagingViewModel";

    private static final int DEFAULT_PAGE_SIZE = 10;

    private static final int DEFAULT_START_PAGE = 1;

    //endregion
    private final List<E> loadedDataList = new ArrayList<>();

    //region Instance Variables

    private final Handler loadDelayHandler = new Handler();

    @Nullable
    private NetPagingCallbacks<E, D> netPagingCallbacks;

    @Nullable
    private Call<M> retrofitCall;

    private int startingPageNumber = DEFAULT_START_PAGE;

    private int pageSize = DEFAULT_PAGE_SIZE;

    private int currentPage = startingPageNumber;

    private boolean isLoading;

    private boolean hasInitialized = false;

    private boolean hasDataFetchFailure = false;
    //endregion

    //region private methods
    private void loadMore() {
        if (isLoading) return;

        if (netPagingCallbacks != null && netPagingCallbacks.getRecyclerAdapter().getData().isEmpty()) {
            setEmptyView(getAdapterConfig().getInitLoadingLayout());
        }

        isLoading = true;
        hasDataFetchFailure = false;

        loadDelayHandler.postDelayed(() -> {
            this.retrofitCall = getRetrofitCall(currentPage);
            retrofitCall.enqueue(new PagingNetworkCallback());
        }, 200);
    }

    private void setEmptyView(@LayoutRes int emptyViewLayout) {
        if (netPagingCallbacks == null) return;
        @Nullable final Context context = netPagingCallbacks.getContext();
        if (context != null && emptyViewLayout != 0) {
            netPagingCallbacks.getRecyclerAdapter().setEmptyView(View.inflate(context, emptyViewLayout, null));
        }
    }

    /**
     * {@link androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener} Implementation
     */
    private void onRefresh() {
        Log.d(TAG, "onRefresh() called");

        if (isLoading) {
            if (netPagingCallbacks != null) {
                netPagingCallbacks.getSwipeRefresh().setRefreshing(false);
            }
            return;
        }

        if (netPagingCallbacks != null) {
            netPagingCallbacks.onSwipeRefreshed();
            if (getAdapterConfig().getInitLoadingLayout() != 0) {
                netPagingCallbacks.getSwipeRefresh().setRefreshing(false);
            }
        }

        refreshList(true);
    }
    //endregion

    //region Lifecycle Method Calls
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume() {
        Log.d(TAG, "onResume() called");
        retryForDataFetchFailure();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        Log.d(TAG, "onDestroy() called");

        if (netPagingCallbacks != null) {
            netPagingCallbacks.lifecycle().removeObserver(this);
        }
        this.hasInitialized = false;
        this.netPagingCallbacks = null;
    }
    //endregion

    //region Public Methods

    /**
     * Set the initial page number to be used when calling first page.
     * Default is {@code DEFAULT_START_PAGE}.
     *
     * @param startingPageNumber Initial Page Number
     */
    public void setStartingPageNumber(int startingPageNumber) {
        this.startingPageNumber = startingPageNumber;
    }

    /**
     * Specify items count per page.
     * Default is {@code DEFAULT_PAGE_SIZE}
     *
     * @param pageSize Number of items per page
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return Whether currently loaded/loading page is first page.
     */
    public boolean isFirstPage() {
        return currentPage == startingPageNumber;
    }

    /**
     * Retry if last network called has failed.
     */
    public void retryForDataFetchFailure() {
        if (hasDataFetchFailure) {
            loadMore();
        }
    }

    /**
     * Reset page number to start fetching from first page.
     *
     * @param reset Clean up and start fetching from @code{startingPageNumber} if true
     */
    public void refreshList(boolean reset) {
        if (isLoading || !hasInitialized) return;

        if (reset) {
            currentPage = startingPageNumber;
            loadedDataList.clear();
            if (netPagingCallbacks != null) {
                netPagingCallbacks.getRecyclerAdapter().getLoadMoreModule().setEnableLoadMore(false);
                netPagingCallbacks.getRecyclerAdapter().setNewInstance(new ArrayList<>());
            }
        }

        loadMore();
    }

    /**
     * Perform hook up operations and initializations
     */
    public void init(@NonNull final NetPagingCallbacks<E, D> netPagingCallbacks) {
        if (hasInitialized) return;

        this.netPagingCallbacks = netPagingCallbacks;
        this.netPagingCallbacks.lifecycle().addObserver(this);

        netPagingCallbacks.getSwipeRefresh().setOnRefreshListener(this::onRefresh);

        netPagingCallbacks.getRecyclerView().setAdapter(netPagingCallbacks.getRecyclerAdapter());

        @Nullable final BaseLoadMoreView loadMoreView = getAdapterConfig().getLoadMoreView();
        @NonNull final BaseLoadMoreModule loadMoreModule = this.netPagingCallbacks.getRecyclerAdapter().getLoadMoreModule();
        if (loadMoreView != null) {
            // Set custom view for loading, error and no more data
            loadMoreModule.setLoadMoreView(loadMoreView);
        }

        // Configuring Endless Loading
        loadMoreModule.setOnLoadMoreListener(this::loadMore);
        loadMoreModule.setAutoLoadMore(true);
        loadMoreModule.setEnableLoadMoreIfNotFullPage(false);
        hasInitialized = true;
    }

    public List<E> getLoadedDataList() {
        return loadedDataList;
    }
    //endregion

    //region Abstract Methods

    /**
     * This method allows to process the list received from
     * retrofit response before showing in adapter.
     *
     * @param data List of data received from retrofit response
     * @return List of data to showing adapter
     */
    @Nullable
    protected abstract List<E> onDataReceived(@NonNull M data);

    @NonNull
    protected abstract NetAdapterConfig getAdapterConfig();

    @NonNull
    protected abstract Call<M> getRetrofitCall(final int currentPage);
    //endregion

    @Override
    protected void onCleared() {
        try {
            if (retrofitCall != null && !retrofitCall.isCanceled()) {
                retrofitCall.cancel();
                retrofitCall = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onCleared();
    }

    private final class PagingNetworkCallback implements Callback<M> {

        @Override
        public void onResponse(@NonNull Call<M> call, @NonNull Response<M> response) {
            onComplete();
            if (response.isSuccessful()) {
                @Nullable final M data = response.body();
                if (data != null) {
                    @Nullable final List<E> fetchedItems = onDataReceived(data);
                    @NonNull final List<E> entities = fetchedItems == null ? new ArrayList<>() : fetchedItems;
                    loadedDataList.addAll(entities);
                    updateUi(entities);
                    currentPage += 1;
                } else {
                    handleFailure();
                }
            } else {
                handleFailure();
            }
        }

        @Override
        public void onFailure(@NonNull Call<M> call, @NonNull Throwable t) {
            if (call.isCanceled()) return;
            onComplete();
            handleFailure();
        }

        private void handleFailure() {
            hasDataFetchFailure = true;
            if (netPagingCallbacks == null) return;
            if (netPagingCallbacks.getRecyclerAdapter().getData().isEmpty()) {
                setEmptyView(getAdapterConfig().getInitFailureLayout());
            }
            netPagingCallbacks.getRecyclerAdapter().getLoadMoreModule().loadMoreFail();
        }

        private void onComplete() {
            Log.d(TAG, "onComplete() called for page number : " + currentPage);
            if (netPagingCallbacks != null) {
                netPagingCallbacks.getRecyclerAdapter().getLoadMoreModule().setEnableLoadMore(true);
            }
            isLoading = false;
        }

        private void updateUi(@NonNull final List<E> entities) {
            if (netPagingCallbacks == null) return;

            if (isFirstPage()) {
                netPagingCallbacks.getRecyclerAdapter().setEmptyView(getAdapterConfig().getEmptyView(netPagingCallbacks.getRecyclerView()));
                netPagingCallbacks.getRecyclerAdapter().setNewInstance(entities);
            } else {
                netPagingCallbacks.getRecyclerAdapter().addData(entities);
            }

            @NonNull final BaseLoadMoreModule loadMoreModule = netPagingCallbacks.getRecyclerAdapter().getLoadMoreModule();
            if (entities.size() < pageSize) {
                Log.d(TAG, "onSuccess: Loaded entities count is less than page size : " + pageSize + ". Ending load more. Assuming no more data");
                loadMoreModule.loadMoreEnd();
            } else {
                loadMoreModule.loadMoreComplete();
            }
        }
    }
}
