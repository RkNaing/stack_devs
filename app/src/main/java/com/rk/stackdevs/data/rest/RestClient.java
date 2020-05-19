package com.rk.stackdevs.data.rest;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rk.stackdevs.BuildConfig;
import com.rk.stackdevs.app.StackDevsApp;
import com.rk.stackdevs.data.rest.endpoints.DevsEndpoint;
import com.rk.stackdevs.data.rest.interceptors.CachingInterceptor;
import com.rk.stackdevs.data.rest.interceptors.NoConnectivityInterceptor;

import java.io.File;

import androidx.annotation.Nullable;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.rk.stackdevs.data.rest.UrlConstants.BASE_URL;


public final class RestClient {

    private static final String TAG = "RestClient";

    private static final long CACHE_SIZE = 100 * 1024 * 1024;

    private static Retrofit sRetrofit;

    private RestClient() {
        //no instance
    }

    private static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            Log.d(TAG, "getRetrofit: Preparing retrofit instance.");
            final OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();
            final Context context = StackDevsApp.getContext();

            // Network Connection Interceptor
            okClientBuilder.addInterceptor(new NoConnectivityInterceptor(context));

            // Cache interceptor
            @Nullable File cacheDir = context.getExternalCacheDir();
            if (cacheDir == null) {
                cacheDir = context.getCacheDir();
            }
            final Cache cache = new Cache(cacheDir, CACHE_SIZE);
            okClientBuilder.cache(cache);
            okClientBuilder.addNetworkInterceptor(new CachingInterceptor());

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                okClientBuilder.addInterceptor(logging);
            }

            final Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();

            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return sRetrofit;
    }

    public static DevsEndpoint getDevsEndpoint() {
        return getRetrofit().create(DevsEndpoint.class);
    }

}
