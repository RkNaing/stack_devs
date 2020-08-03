package com.rk.stackdevs.ui.adapters;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.rk.stackdevs.R;
import com.rk.stackdevs.app.GlideRequests;
import com.rk.stackdevs.models.Badge;
import com.rk.stackdevs.models.Dev;
import com.rk.stackdevs.ui.views.BadgeView;
import com.rk.stackdevs.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created by Rahul Kumar on 07/05/2020.
 **/
public final class DevsListRVAdapter extends BaseQuickAdapter<Dev, BaseViewHolder> implements LoadMoreModule {

    @NonNull
    private final GlideRequests glideRequests;

    private final RequestOptions requestOptions;

    public DevsListRVAdapter(@NonNull GlideRequests glideRequests) {
        super(R.layout.item_dev);
        this.glideRequests = glideRequests;
        requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.img_placeholder_profile)
                .error(R.drawable.img_placeholder_profile)
                .fallback(R.drawable.img_placeholder_profile)
                .apply(RequestOptions.circleCropTransform());
        setAnimationEnable(true);
        setAnimationFirstOnly(true);
        setAnimationWithDefault(AnimationType.ScaleIn);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Dev dev) {
        if (dev == null) return;

        final AppCompatImageView ivProfile = baseViewHolder.getView(R.id.item_dev_ivProfile);
        glideRequests
                .load(dev.getPhotoUrl())
                .apply(requestOptions)
                .into(ivProfile);

        final MaterialTextView tvDevName = baseViewHolder.getView(R.id.item_dev_tvDevName);
        AppUtils.setTextAsync(tvDevName, dev.getName());

        final String reputationScoreStr = AppUtils.getCompatNumberRepresentation(dev.getReputationScore());
        final String reputationScoreLbl = getContext().getString(R.string.lbl_reputation, reputationScoreStr);
        final MaterialTextView tvDevReputation = baseViewHolder.getView(R.id.item_dev_tvDevReputation);
        AppUtils.setTextAsync(tvDevReputation, reputationScoreLbl);

        final MaterialTextView tvDevLocation = baseViewHolder.getView(R.id.item_dev_tvDevLocation);
        AppUtils.setTextAsync(tvDevLocation, dev.getLocation());

        final BadgeView bvBronze = baseViewHolder.getView(R.id.item_dev_badgeBronze);
        final BadgeView bvSilver = baseViewHolder.getView(R.id.item_dev_badgeSilver);
        final BadgeView bvGold = baseViewHolder.getView(R.id.item_dev_badgeGold);
        final MaterialTextView tvLblBadges = baseViewHolder.getView(R.id.item_dev_tvLblBadges);

        @Nullable final Badge badge = dev.getBadge();
        if (badge != null && badge.hasData()) {
            tvLblBadges.setVisibility(View.VISIBLE);
            showBadge(bvBronze, badge.getBronze());
            showBadge(bvSilver, badge.getSilver());
            showBadge(bvGold, badge.getGold());
        } else {
            tvLblBadges.setVisibility(View.GONE);
            bvBronze.setVisibility(View.GONE);
            bvSilver.setVisibility(View.GONE);
            bvGold.setVisibility(View.GONE);
        }

        final MaterialButton btnViewProfile = baseViewHolder.getView(R.id.item_dev_btnViewProfile);
        @Nullable final String profileUrl = dev.getProfileUrl();
        if (!TextUtils.isEmpty(profileUrl)) {
            btnViewProfile.setVisibility(View.VISIBLE);
            btnViewProfile.setOnClickListener(v -> openWebUrl(profileUrl));
        } else {
            btnViewProfile.setVisibility(View.GONE);
        }

        final MaterialButton btnVisitWeb = baseViewHolder.getView(R.id.item_dev_btnVisitWeb);
        @Nullable final String webUrl = dev.getWebUrl();
        if (!TextUtils.isEmpty(webUrl)) {
            btnVisitWeb.setVisibility(View.VISIBLE);
            btnVisitWeb.setOnClickListener(v -> openWebUrl(webUrl));
        } else {
            btnVisitWeb.setVisibility(View.GONE);
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
            getContext().startActivity(browserIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
