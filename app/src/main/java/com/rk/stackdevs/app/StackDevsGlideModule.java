package com.rk.stackdevs.app;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.rk.stackdevs.BuildConfig;

import androidx.annotation.NonNull;

/**
 * Created by Rahul Kumar on 07/05/2020.
 *
 * App's Glide Module
 **/
@GlideModule
public final class StackDevsGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        if (BuildConfig.DEBUG) {
            builder.setLogLevel(Log.VERBOSE);
        }
    }

}
