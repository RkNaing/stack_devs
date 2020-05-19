package com.rk.stackdevs.ui.viewmodels.factories;

import android.content.Context;

import com.rk.stackdevs.ui.viewmodels.ContextViewModel;

import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by Rahul Kumar on 15/05/2020.
 **/
public final class ContextViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final Context context;

    public ContextViewModelFactory(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (ContextViewModel.class.isAssignableFrom(modelClass)) {
            try {
                return modelClass.getConstructor(Context.class).newInstance(context);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            }
        }
        throw new IllegalArgumentException("Unsupported ViewModel class : Extend ContextViewModel to instantiate.");
    }
}
