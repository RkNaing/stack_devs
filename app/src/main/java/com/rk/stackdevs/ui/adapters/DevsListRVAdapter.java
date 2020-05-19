package com.rk.stackdevs.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rk.stackdevs.app.GlideRequests;
import com.rk.stackdevs.data.DataRequestStatus;
import com.rk.stackdevs.databinding.ItemDevBinding;
import com.rk.stackdevs.databinding.ItemListFooterBinding;
import com.rk.stackdevs.models.Dev;
import com.rk.stackdevs.ui.adapters.viewholders.DevHolder;
import com.rk.stackdevs.ui.adapters.viewholders.FooterHolder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Rahul Kumar on 07/05/2020.
 **/
public final class DevsListRVAdapter extends PagedListAdapter<Dev, RecyclerView.ViewHolder> {

    private static final int ITEM_FOOTER = 942;
    private static final int ITEM_DATA = 777;

    @NonNull
    private final GlideRequests glideRequests;

    @Nullable
    private DataRequestStatus status;

    public DevsListRVAdapter(@NonNull GlideRequests glideRequests) {
        super(new DiffUtil.ItemCallback<Dev>() {
            @Override
            public boolean areItemsTheSame(@NonNull Dev oldItem, @NonNull Dev newItem) {
                return oldItem.getId() == newItem.getId() &&
                        oldItem.getAccountId() == newItem.getAccountId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Dev oldItem, @NonNull Dev newItem) {
                return oldItem.equals(newItem);
            }
        });
        this.glideRequests = glideRequests;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @NonNull final Context context = parent.getContext();
        @NonNull final LayoutInflater layoutInflater = LayoutInflater.from(context);

        if (viewType == ITEM_FOOTER) {
            final ItemListFooterBinding itemListFooterBinding = ItemListFooterBinding.inflate(layoutInflater, parent, false);
            return new FooterHolder(itemListFooterBinding);
        } else {
            final ItemDevBinding itemDevBinding = ItemDevBinding.inflate(layoutInflater, parent, false);
            return new DevHolder(itemDevBinding, glideRequests);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof DevHolder) {
            @Nullable final Dev dev = getItem(position);
            if (dev != null) {
                ((DevHolder) holder).bindDev(dev);
            }
        } else if (holder instanceof FooterHolder) {
            ((FooterHolder) holder).bindFooter(status);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (hasFooterRow() && position == getItemCount() - 1) ? ITEM_FOOTER : ITEM_DATA;
    }

    public void setStatus(@Nullable DataRequestStatus newStatus) {
        final DataRequestStatus prevStatus = this.status;
        final boolean hadFooterRow = hasFooterRow();
        this.status = newStatus;
        final boolean hasFooterRow = hasFooterRow();

        if (hadFooterRow != hasFooterRow) {
            if (hadFooterRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (hasFooterRow && (prevStatus != this.status)) {
            notifyItemChanged(getItemCount() - 1);
        }

    }

    private boolean hasFooterRow() {
        return this.status != null && !this.status.isSuccess();
    }
}
