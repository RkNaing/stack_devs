package com.rk.stackdevs.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import java.text.DecimalFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.text.PrecomputedTextCompat;

/**
 * Created by Rahul Kumar on 07/05/2020.
 **/
@SuppressWarnings("WeakerAccess")
public final class AppUtils {

    private static final String TAG = "AppUtils";

    private AppUtils() {
        //no instance
    }

    public static boolean isInternetConnected(@NonNull Context context) {
        boolean isConnected = false;
        try {
            @Nullable final Object systemService = context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (systemService instanceof ConnectivityManager) {
                @NonNull final ConnectivityManager connectivityManager = (ConnectivityManager) systemService;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    @Nullable final Network activeNetwork = connectivityManager.getActiveNetwork();
                    if (activeNetwork != null) {
                        @Nullable final NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
                        if (networkCapabilities != null) {
                            isConnected = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
                        }
                    }
                } else {
                    @Nullable final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null) {
                        isConnected = activeNetworkInfo.isConnected();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "isInternetConnected: ", e);
        }
        Log.d(TAG, "isInternetConnected() returned: " + isConnected);
        return isConnected;
    }

    public static String getCompatNumberRepresentation(long number) {
        Log.d(TAG, "getCompatNumberRepresentation() called with: number = [" + number + "]");
        if (number < 1000) return String.valueOf(number);
        int exp = (int) (Math.log(number) / Math.log(1000));
        final DecimalFormat decimalFormat = new DecimalFormat("#.##");
        final String value = decimalFormat.format(number / Math.pow(1000, exp));
        final char unit = "kMGTPE".charAt(exp - 1);
        final String compatRepresentation = value + " " + unit;
        Log.d(TAG, "getCompatNumberRepresentation() returned: " + compatRepresentation);
        return compatRepresentation;
    }

    public static void setTextAsync(@NonNull AppCompatTextView textView, @Nullable CharSequence text) {
        textView.setTextFuture(PrecomputedTextCompat.getTextFuture(emptyOnNull(text), textView.getTextMetricsParamsCompat(), null));
    }

    @NonNull
    public static CharSequence emptyOnNull(@Nullable CharSequence source) {
        return (source == null || source.length() == 0) ? "" : source;
    }

}
