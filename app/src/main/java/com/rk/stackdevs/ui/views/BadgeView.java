package com.rk.stackdevs.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.rk.stackdevs.R;
import com.rk.stackdevs.databinding.ViewBadgeBinding;
import com.rk.stackdevs.utils.AppUtils;

import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import static android.graphics.PorterDuff.Mode.SRC_IN;

/**
 * Custom View for showing developer badge
 */
public final class BadgeView extends RelativeLayout {

    private static final int BADGE_TYPE_BRONZE = 15;

    private static final int BADGE_TYPE_GOLD = 16;

    private static final int BADGE_TYPE_SILVER = 17;

    private final ViewBadgeBinding binding;

    public BadgeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        binding = ViewBadgeBinding.inflate(LayoutInflater.from(context),this,true);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BadgeView);
        final int badgeType = typedArray.getInt(R.styleable.BadgeView_badge_type, BADGE_TYPE_SILVER);
        typedArray.recycle();

        @ColorRes final int badgeTint;

        @StringRes final int badgeTitle;

        switch (badgeType) {
            case BADGE_TYPE_BRONZE:
                badgeTint = R.color.badgeBronze;
                badgeTitle = R.string.lbl_bronze;
                break;
            case BADGE_TYPE_GOLD:
                badgeTint = R.color.badgeGold;
                badgeTitle = R.string.lbl_gold;
                break;
            default:
                badgeTint = R.color.badgeSilver;
                badgeTitle = R.string.lbl_silver;
        }

        binding.badgeTvBadgeTitle.setText(badgeTitle);


        final int tintColor = ContextCompat.getColor(context, badgeTint);
        binding.badgeRlContainer.getBackground().setTint(tintColor);
        binding.badgeTvBadgeTitle.getBackground().setTint(tintColor);
        binding.badgeTvScore.getBackground().setTint(tintColor);
        binding.badgeIvBadgeLogo.setColorFilter(tintColor, SRC_IN);

        if (isInEditMode()) {
            binding.badgeTvScore.setText(String.valueOf(123456));
        }

    }

    public void setScore(int score) {
        AppUtils.setTextAsync(binding.badgeTvScore,String.valueOf(score));
    }

}
