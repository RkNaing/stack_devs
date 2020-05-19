package com.rk.stackdevs.ui.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

/**
 * Created by Rahul Kumar on 15/05/2020.
 **/
public class ContextViewModel extends ViewModel {

    @NonNull
    private final Context context;

    public ContextViewModel(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    public final Context getContext() {
        return context;
    }
}
