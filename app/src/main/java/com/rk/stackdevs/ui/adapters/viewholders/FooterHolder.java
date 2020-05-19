package com.rk.stackdevs.ui.adapters.viewholders;

import com.rk.stackdevs.R;
import com.rk.stackdevs.data.DataRequestStatus;
import com.rk.stackdevs.databinding.ItemListFooterBinding;
import com.rk.stackdevs.models.ListItemInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Rahul Kumar on 10/05/2020.
 **/
public final class FooterHolder extends RecyclerView.ViewHolder {

    @NonNull
    private final ItemListFooterBinding binding;

    public FooterHolder(@NonNull ItemListFooterBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindFooter(@Nullable DataRequestStatus dataRequestStatus) {

        if (dataRequestStatus == null) {
            binding.itemFooterInfo.clear();
            return;
        }

        if (dataRequestStatus.getStatus() == DataRequestStatus.LOADING) {
            binding.itemFooterInfo.showLoading(R.string.msg_loading_more_devs);
        } else if (dataRequestStatus.getStatus() == DataRequestStatus.FAILED) {
            @NonNull final ListItemInfo listItemInfo = ListItemInfo.from(dataRequestStatus);
            binding.itemFooterInfo.showMessage(listItemInfo.getErrorMessage(), listItemInfo.getErrorIcon());
            binding.itemFooterInfo.setOnClickListener(v -> dataRequestStatus.retry());
        }

    }
}
