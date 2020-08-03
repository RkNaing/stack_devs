package com.rk.stackdevs.ui.components.net_paging;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.loadmore.BaseLoadMoreView;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.rk.stackdevs.R;

import androidx.annotation.NonNull;


public final class AppLoadMoreView extends BaseLoadMoreView {
    @NonNull
    @Override
    public View getLoadComplete(@NonNull BaseViewHolder baseViewHolder) {
        return baseViewHolder.getView(R.id.listItemLoadMoreTvEnd);
    }

    @NonNull
    @Override
    public View getLoadEndView(@NonNull BaseViewHolder baseViewHolder) {
        return baseViewHolder.getView(R.id.listItemLoadMoreTvEnd);
    }

    @NonNull
    @Override
    public View getLoadFailView(@NonNull BaseViewHolder baseViewHolder) {
        return baseViewHolder.getView(R.id.listItemLoadMoreErrorContainer);
    }

    @NonNull
    @Override
    public View getLoadingView(@NonNull BaseViewHolder baseViewHolder) {
        return baseViewHolder.getView(R.id.listItemLoadMorePbLoading);
    }

    @NonNull
    @Override
    public View getRootView(@NonNull ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_load_more, parent, false);
    }
}
