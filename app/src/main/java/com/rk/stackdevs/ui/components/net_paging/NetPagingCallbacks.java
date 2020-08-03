package com.rk.stackdevs.ui.components.net_paging;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by ZMN on 29/04/2020.
 * <p>
 * Callbacks for facilitating {@link NetPagingViewModel}
 **/
public interface NetPagingCallbacks<E, D extends BaseQuickAdapter<E, ? extends BaseViewHolder>> {

    @Nullable
    Context getContext();

    @NonNull
    Lifecycle lifecycle();

    @NonNull
    RecyclerView getRecyclerView();

    @NonNull
    D getRecyclerAdapter();

    @NonNull
    SwipeRefreshLayout getSwipeRefresh();

    void onSwipeRefreshed();

}
