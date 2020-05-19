package com.rk.stackdevs.data.rest.interceptors;

import android.util.Log;

import com.rk.stackdevs.data.rest.UrlConstants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Rahul Kumar on 16/05/2020.
 *
 * OkHttp caching interceptor with conditional caching support
 **/
public final class CachingInterceptor implements Interceptor {

    private static final String TAG = "CachingInterceptor";

    private static final int MAX_AGE = 7;

    private static final int MAX_STALE = 7;

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        @NonNull Request request = chain.request();

        Log.d(TAG, "intercept: All Headers : " + request.headers());

        @Nullable final String cacheHeader = request.header(UrlConstants.CACHE_HEADER);
        Log.d(TAG, "intercept: Cache Header : " + cacheHeader);
        final boolean shouldCache = Boolean.parseBoolean(cacheHeader);

        request = request.newBuilder().removeHeader(UrlConstants.CACHE_HEADER).build();

        @NonNull final Response response = chain.proceed(request);

        // Avoid caching with the request doesn't have Caching Flag in header
        if (!shouldCache) {
            Log.d(TAG, "intercept: Avoid Cache based on " + UrlConstants.CACHE_HEADER);
            return response;
        }

        final CacheControl cacheControl = new CacheControl.Builder()
                .maxAge(MAX_AGE, TimeUnit.DAYS)
                .maxStale(MAX_STALE, TimeUnit.DAYS)
                .build();

        return response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build();
    }
}
