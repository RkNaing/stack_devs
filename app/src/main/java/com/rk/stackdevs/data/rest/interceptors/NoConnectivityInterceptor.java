package com.rk.stackdevs.data.rest.interceptors;

import android.content.Context;
import android.util.Log;

import com.rk.stackdevs.data.rest.NoConnectivityException;
import com.rk.stackdevs.data.rest.UrlConstants;
import com.rk.stackdevs.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

final public class NoConnectivityInterceptor implements Interceptor {

    private static final String TAG = "NoConnInterceptor";

    @NonNull
    private final Context mContext;

    public NoConnectivityInterceptor(@NonNull Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        @NonNull Request request = chain.request();
        @Nullable final String cacheHeader = request.header(UrlConstants.CACHE_HEADER);
        final boolean shouldCache = Boolean.parseBoolean(cacheHeader);

        final Request.Builder requestBuilder = request.newBuilder();
//        requestBuilder.removeHeader(UrlConstants.CACHE_HEADER);

        if (!AppUtils.isInternetConnected(mContext) && !shouldCache) {
            Log.d(TAG, "intercept: No internet & not cacheable. Throwing NoConnectivityException.");
            throw new NoConnectivityException();
        }

        request = requestBuilder.build();

        return chain.proceed(request);
    }
}
