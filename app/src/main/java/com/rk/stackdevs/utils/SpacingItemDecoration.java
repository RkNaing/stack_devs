package com.rk.stackdevs.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Rahul Kumar on 2019-12-26.
 **/
public final class SpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final int spacingInPixel;

    public SpacingItemDecoration(@NonNull Context context, @DimenRes int spacingDimen) {
        this.spacingInPixel = context.getResources().getDimensionPixelOffset(spacingDimen);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        @Nullable final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutManager == null) {
            return;
        }

        final int childAdapterPosition = parent.getChildAdapterPosition(view);

        if (layoutManager instanceof GridLayoutManager) {

            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final int spanCount = gridLayoutManager.getSpanCount();
            final int column = childAdapterPosition % spanCount;
            outRect.left = column * spacingInPixel / spanCount;
            outRect.right = spacingInPixel - (column + 1) * spacingInPixel / spanCount;
            if (childAdapterPosition >= spanCount) {
                outRect.top = spacingInPixel;
            }

        } else if (layoutManager instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;

            if (childAdapterPosition <= 0) {
                return;
            }

            if (linearLayoutManager.getOrientation() == RecyclerView.VERTICAL) {
                outRect.top = spacingInPixel;
            } else {
                outRect.left = spacingInPixel;
            }
        }

    }
}
