package com.rk.stackdevs.ui.components.net_paging;

import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.loadmore.BaseLoadMoreView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Zaw Myo Naing on 7/16/18.
 * <pre>
 *     Description  :   Required Config Provider Interface for
 * </pre>
 **/
public interface NetAdapterConfig {

    @LayoutRes
    int getInitLoadingLayout();

    @LayoutRes
    int getInitFailureLayout();

    @NonNull
    View getEmptyView(@NonNull ViewGroup parent);

    @Nullable
    BaseLoadMoreView getLoadMoreView();

}
