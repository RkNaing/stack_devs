package com.rk.stackdevs.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.rk.stackdevs.databinding.ViewListInformationAccessoryBinding;
import com.rk.stackdevs.utils.AppUtils;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

/**
 * Created by ZMN on 18/05/2020.
 *
 * Custom view for showing list information such as loading, error, empty, etc.
 **/
public final class ListInformationAccessoryView extends FrameLayout {

    private static final String TAG = "ListInfoAccessoryView";

    private final ViewListInformationAccessoryBinding binding;

    public ListInformationAccessoryView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        binding = ViewListInformationAccessoryBinding.inflate(LayoutInflater.from(context), this, true);
    }

    public void showLoading(@StringRes int message) {
        showLoading(getContext().getString(message));
    }

    public void showLoading(@Nullable String message) {
        Log.d(TAG, "showLoading() called with: message = [" + message + "]");
        binding.listInfoLoadingContainer.setVisibility(VISIBLE);
        binding.listInfoMessageContainer.setVisibility(GONE);
        AppUtils.setTextAsync(binding.listInfoTvLoading, message);
    }

    public void showMessage(@StringRes int message, @DrawableRes int icon) {
        showMessage(getContext().getString(message), icon);
    }

    public void showMessage(@Nullable String message, @DrawableRes int icon) {
        Log.d(TAG, "showMessage() called with: message = [" + message + "], icon = [" + icon + "]");
        binding.listInfoMessageContainer.setVisibility(VISIBLE);
        binding.listInfoLoadingContainer.setVisibility(GONE);
        AppUtils.setTextAsync(binding.listInfoTvMessage, message);
        binding.listInfoIvIcon.setImageResource(icon);
    }

    public void clear() {
        binding.listInfoLoadingContainer.setVisibility(GONE);
        binding.listInfoMessageContainer.setVisibility(GONE);
    }
}
