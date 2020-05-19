package com.rk.stackdevs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by Rahul Kumar on 08/05/2020.
 **/
public final class DevsListResponse {

    @Expose
    @SerializedName("items")
    private List<Dev> devs;

    @Expose
    @SerializedName("has_more")
    private boolean hasMore;

    public List<Dev> getDevs() {
        return devs;
    }

    public void setDevs(List<Dev> devs) {
        this.devs = devs;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    @NonNull
    public List<Dev> requireDevs() {
        return devs != null ? devs : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "DevsListResponse{" +
                "devs=" + devs +
                ", hasMore=" + hasMore +
                '}';
    }
}
