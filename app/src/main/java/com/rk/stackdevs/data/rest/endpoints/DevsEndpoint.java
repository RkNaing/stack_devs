package com.rk.stackdevs.data.rest.endpoints;

import com.rk.stackdevs.data.rest.UrlConstants;
import com.rk.stackdevs.models.DevsListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Rahul Kumar on 07/05/2020.
 **/
public interface DevsEndpoint {

    @Headers(UrlConstants.CACHE_HEADER + ": "+ true)
    @GET("users?order=desc&sort=reputation&site=stackoverflow")
    Call<DevsListResponse> getDevsList(@Query("page") int page, @Query("pagesize") int pageSize);
}
