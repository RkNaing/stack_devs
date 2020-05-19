package com.rk.stackdevs.app;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Rahul Kumar on 07/05/2020.
 *
 * StackDevs AppController
 **/
public final class StackDevsApp extends Application {

    @Nullable
    private static StackDevsApp stackDevsApp;

    /**
     * Helper method to provide access to application's context instance
     * across this application.
     *
     * @throws IllegalStateException if called before Application's {@link StackDevsApp#onCreate()}
     * @return  Non-Null Context Instance
     */
    @NonNull
    public static Context getContext() {
        if (stackDevsApp == null) {
            throw new IllegalStateException("Application has not been created yet.");
        }
        return stackDevsApp.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        stackDevsApp = this;
    }
}
