package com.rk.stackdevs.ui.components.net_paging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.loadmore.BaseLoadMoreView;
import com.google.android.material.textview.MaterialTextView;
import com.rk.stackdevs.R;
import com.rk.stackdevs.utils.AppUtils;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatImageView;

public class NetAdapterConfigImpl implements NetAdapterConfig {

    private final AppLoadMoreView appLoadMoreView = new AppLoadMoreView();

    @StringRes
    private int emptyMessage;

    @DrawableRes
    private int emptyIcon;

    public NetAdapterConfigImpl(@StringRes int emptyMessage, @DrawableRes int emptyIcon) {
        this.emptyMessage = emptyMessage;
        this.emptyIcon = emptyIcon;
    }

    @Override
    public int getInitLoadingLayout() {
        return R.layout.list_item_load_more_initial_loading;
    }

    @Override
    public int getInitFailureLayout() {
        return R.layout.list_item_load_more_initial_error;
    }

    @NonNull
    @Override
    public View getEmptyView(@NonNull ViewGroup parent) {
        final Context context = parent.getContext();
        final View emptyView = LayoutInflater.from(context).inflate(R.layout.layout_list_empty_view, parent, false);
        final MaterialTextView tvEmptyMessage = emptyView.findViewById(R.id.listEmptyView_tvMsg);
        AppUtils.setTextAsync(tvEmptyMessage, context.getString(emptyMessage));

        final AppCompatImageView ivEmptyIcon = emptyView.findViewById(R.id.listEmptyView_ivIcon);
        ivEmptyIcon.setImageResource(emptyIcon);

        return emptyView;
    }

    @Nullable
    @Override
    public BaseLoadMoreView getLoadMoreView() {
        return appLoadMoreView;
    }
}
