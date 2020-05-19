package com.rk.stackdevs.data;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Rahul Kumar on 08/05/2020.
 *
 * Status indicator for data request.
 **/
@SuppressWarnings("WeakerAccess")
public final class DataRequestStatus {

    /**
     * Indicates the data request is in progress.
     */
    public static final int LOADING = 204;

    /**
     * Indicates data request failure
     */
    public static final int FAILED = 158;

    /**
     * Indicates data request completed successfully.
     */
    public static final int SUCCESS = 80;

    /**
     * Indicates data request in not being executed,i.e, is in idle state
     */
    public static final int IDLE = 422;

    private final int status;

    @Nullable
    private final DataRequestException exception;

    @Nullable
    private final Runnable retryRunnable;

    /**
     * @param status Status of the data request {@link Status}
     * @param exception {@link DataRequestException} instance in case of failure
     * @param retryRunnable {@link Runnable} to perform retry in case of failure
     */
    public DataRequestStatus(@Status int status, @Nullable DataRequestException exception, @Nullable Runnable retryRunnable) {
        this.status = status;
        this.exception = exception;
        this.retryRunnable = retryRunnable;
    }

    /**
     * @return An instance of {@link DataRequestStatus} indicating loading status
     */
    @NonNull
    public static DataRequestStatus loading() {
        return new DataRequestStatus(LOADING, null,null);
    }

    /**
     * @return An instance of {@link DataRequestStatus} indicating success status
     */
    @NonNull
    public static DataRequestStatus success() {
        return new DataRequestStatus(SUCCESS, null,null);
    }

    /**
     * @return An instance of {@link DataRequestStatus} indicating failed status
     */
    @NonNull
    public static DataRequestStatus failed(@Nullable DataRequestException exception,@Nullable Runnable retryRunnable) {
        return new DataRequestStatus(FAILED, exception,retryRunnable);
    }

    @Status
    public int getStatus() {
        return status;
    }

    public boolean isSuccess(){
        return this.status == SUCCESS;
    }

    public boolean isFail(){
        return this.status == FAILED;
    }

    public boolean isLoading(){
        return this.status == LOADING;
    }

    public void retry(){
        if (retryRunnable != null) {
            retryRunnable.run();
        }
    }

    @Nullable
    public DataRequestException getException() {
        return exception;
    }

    @Override
    public String toString() {
        return "DataRequestStatus{" +
                "status=" + status +
                ", exception=" + exception +
                '}';
    }

    /**
     * Data request Status indicator flags
     */
    @IntDef({LOADING, FAILED, SUCCESS, IDLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {

    }

}
