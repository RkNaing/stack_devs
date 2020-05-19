package com.rk.stackdevs.data.rest;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rk.stackdevs.data.rest.NetCallResult.CLIENT_ERROR;
import static com.rk.stackdevs.data.rest.NetCallResult.CLIENT_FAIL;
import static com.rk.stackdevs.data.rest.NetCallResult.FAILED;
import static com.rk.stackdevs.data.rest.NetCallResult.NO_DATA;
import static com.rk.stackdevs.data.rest.NetCallResult.SERVER_ERROR;

/**
 * Created by Zaw Myo Naing on 3/18/18.
 **/
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class RetrofitCallbackHelper<T> implements Callback<T> {
    /**
     * Flag to indicate when no http response code is received
     */
    public static final int NO_RESPONSE_CODE = -3246;

    private static final String TAG = "RetrofitCallbackHelper";

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        onComplete();
        final int responseCode = response.code();
        Log.d(TAG, "onResponse: Server Response Code : " + responseCode);
        if (response.isSuccessful()) {
            T data = response.body();
            if (data != null) {
                onSuccess(data, responseCode);
            } else {
                onFailure(new Throwable("Received Invalid data from response"), responseCode, NO_DATA);
            }
        } else if (responseCode >= 400 && responseCode < 500) {
            String errMsg = "There are some problems in http request parameter.";
            onFailure(new Throwable(errMsg), responseCode, CLIENT_ERROR);
        } else if (responseCode >= 500) {
            String errMsg = "There are some problem in server.";
            onFailure(new Throwable(errMsg), responseCode, SERVER_ERROR);
        } else {
            String errMsg = "Unsuccessful Response\nResponse Code : " + response.code() + "\nMessage: " + response.message();
            onFailure(new Throwable(errMsg), responseCode, FAILED);
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        final boolean canceled = call.isCanceled();
        Log.d(TAG, "onFailure: Is Call Canceled ? : " + canceled);
        if (!canceled) {
            onComplete();
            String errMsg = !TextUtils.isEmpty(t.getMessage()) ? t.getMessage() : "Unknown Error";
            onFailure(new Throwable(errMsg), NO_RESPONSE_CODE, CLIENT_FAIL);
        }
    }

    protected abstract void onSuccess(@NonNull T data, int responseCode);

    protected abstract void onFailure(@NonNull Throwable t, int responseCode, @NetCallResult int resultCode);

    /**
     * Called when http request is finished regardless of success or failure
     */
    protected abstract void onComplete();

}
