package com.rk.stackdevs.data.rest;

import java.io.IOException;

import androidx.annotation.NonNull;

/**
 * An exception class for the indication of internet disconnection.
 */
public final class NoConnectivityException extends IOException {

    @NonNull
    @Override
    public String getMessage() {
        return "No internet access available currently.";
    }
}
