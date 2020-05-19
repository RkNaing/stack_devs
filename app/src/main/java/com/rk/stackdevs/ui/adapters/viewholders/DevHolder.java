package com.rk.stackdevs.ui.adapters.viewholders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rk.stackdevs.R;
import com.rk.stackdevs.app.GlideRequests;
import com.rk.stackdevs.databinding.ItemDevBinding;
import com.rk.stackdevs.models.Badge;
import com.rk.stackdevs.models.Dev;
import com.rk.stackdevs.ui.views.BadgeView;
import com.rk.stackdevs.utils.AppUtils;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Rahul Kumar on 07/05/2020.
 **/
public final class DevHolder extends RecyclerView.ViewHolder {

    @NonNull
    private final ItemDevBinding binding;

    @NonNull
    private final Context mContext;

    @NonNull
    private final GlideRequests glideRequests;

    public DevHolder(@NonNull ItemDevBinding binding, @NonNull GlideRequests glideRequests) {
        super(binding.getRoot());
        this.mContext = binding.getRoot().getContext();
        this.binding = binding;
        this.glideRequests = glideRequests;
    }

    public void bindDev(@NonNull Dev dev) {

        @DrawableRes final int img_placeholder_profile = R.drawable.img_placeholder_profile;
        glideRequests
                .load(dev.getPhotoUrl())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(img_placeholder_profile)
                .error(img_placeholder_profile)
                .fallback(img_placeholder_profile)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.itemDevIvProfile);


        AppUtils.setTextAsync(binding.itemDevTvDevName, dev.getName());

        final String reputationScoreStr = AppUtils.getCompatNumberRepresentation(dev.getReputationScore());
        final String reputationScoreLbl = mContext.getString(R.string.lbl_reputation, reputationScoreStr);
        AppUtils.setTextAsync(binding.itemDevTvDevReputation, reputationScoreLbl);

        AppUtils.setTextAsync(binding.itemDevTvDevLocation, dev.getLocation());

        @Nullable final Badge badge = dev.getBadge();
        if (badge != null && badge.hasData()) {
            binding.itemDevTvLblBadges.setVisibility(View.VISIBLE);
            showBadge(binding.itemDevBadgeBronze, badge.getBronze());
            showBadge(binding.itemDevBadgeSilver, badge.getSilver());
            showBadge(binding.itemDevBadgeGold, badge.getGold());
        } else {
            binding.itemDevTvLblBadges.setVisibility(View.GONE);
            binding.itemDevBadgeBronze.setVisibility(View.GONE);
            binding.itemDevBadgeSilver.setVisibility(View.GONE);
            binding.itemDevBadgeGold.setVisibility(View.GONE);
        }

        @Nullable final String profileUrl = dev.getProfileUrl();
        if (!TextUtils.isEmpty(profileUrl)) {
            binding.itemDevBtnViewProfile.setVisibility(View.VISIBLE);
            binding.itemDevBtnViewProfile.setOnClickListener(v -> openWebUrl(profileUrl));
        } else {
            binding.itemDevBtnViewProfile.setVisibility(View.GONE);
        }

        @Nullable final String webUrl = dev.getWebUrl();
        if (!TextUtils.isEmpty(webUrl)) {
            binding.itemDevBtnVisitWeb.setVisibility(View.VISIBLE);
            binding.itemDevBtnVisitWeb.setOnClickListener(v -> openWebUrl(webUrl));
        } else {
            binding.itemDevBtnVisitWeb.setVisibility(View.GONE);
        }

    }

    private void showBadge(@NonNull final BadgeView badgeView, int score) {
        if (score > 0) {
            badgeView.setVisibility(View.VISIBLE);
            badgeView.setScore(score);
        } else {
            badgeView.setVisibility(View.GONE);
        }
    }

    private void openWebUrl(@NonNull String webUrl) {
        if (TextUtils.isEmpty(webUrl)) return;
        final Uri url = Uri.parse(webUrl);
        final Intent browserIntent = new Intent(Intent.ACTION_VIEW, url);
        try {
            mContext.startActivity(browserIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
