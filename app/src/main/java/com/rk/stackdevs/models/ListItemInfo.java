package com.rk.stackdevs.models;

import com.rk.stackdevs.R;
import com.rk.stackdevs.data.DataRequestException;
import com.rk.stackdevs.data.DataRequestStatus;
import com.rk.stackdevs.data.rest.NetCallResult;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

/**
 * Created by Rahul Kumar on 15/05/2020.
 **/
public final class ListItemInfo {

    @DrawableRes
    private final int errorIcon;

    @StringRes
    private final int errorMessage;

    private ListItemInfo(@DrawableRes int errorIcon, @StringRes int errorMessage) {
        this.errorIcon = errorIcon;
        this.errorMessage = errorMessage;
    }

    @NonNull
    public static ListItemInfo from(@NonNull DataRequestStatus dataRequestStatus) {
        @DrawableRes int errorIcon = R.drawable.img_connect_failed;
        @StringRes int errorMessage = R.string.msg_unknown_connection_error;

        @Nullable final DataRequestException exception = dataRequestStatus.getException();
        if (exception != null) {
            if (exception.getType() == NetCallResult.CLIENT_FAIL) {
                errorIcon = R.drawable.img_no_internet;
                errorMessage = R.string.msg_no_internet;
            } else if (exception.getType() == NetCallResult.SERVER_ERROR) {
                errorIcon = R.drawable.img_server_error;
                errorMessage = R.string.msg_server_error;
            }
        }
        return new ListItemInfo(errorIcon, errorMessage);
    }

    public int getErrorIcon() {
        return errorIcon;
    }

    public int getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "ListItemInfo{" +
                "errorIcon=" + errorIcon +
                ", errorMessage=" + errorMessage +
                '}';
    }
}
