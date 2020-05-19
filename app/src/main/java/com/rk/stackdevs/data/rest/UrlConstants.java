package com.rk.stackdevs.data.rest;

public final class UrlConstants {

    private static final String DOMAIN = "https://api.stackexchange.com/";

    private static final String API_VERSION = "2.2/";

    static final String BASE_URL = DOMAIN + API_VERSION;

    /**
     * Custom request header to indicate that a specific request should be cached
     */
    public static final String CACHE_HEADER = "App-Cache";

}
