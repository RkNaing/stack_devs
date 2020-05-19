package com.rk.stackdevs.data.rest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

import static com.rk.stackdevs.data.rest.NetCallResult.CLIENT_ERROR;
import static com.rk.stackdevs.data.rest.NetCallResult.CLIENT_FAIL;
import static com.rk.stackdevs.data.rest.NetCallResult.FAILED;
import static com.rk.stackdevs.data.rest.NetCallResult.NO_DATA;
import static com.rk.stackdevs.data.rest.NetCallResult.SERVER_ERROR;

/**
 * Created by Rahul Kumar on 09/05/2020.
 **/

@IntDef({FAILED, CLIENT_ERROR, SERVER_ERROR, CLIENT_FAIL, NO_DATA})
@Retention(RetentionPolicy.SOURCE)
public @interface NetCallResult {

    /**
     * Flag to indicate http response code is not within 200...300
     */
    int FAILED = 9384;

    /**
     * Flag to indicate http response code 400...414
     */
    int CLIENT_ERROR = 9428;

    /**
     * Flag to indicate http response code 500 and above
     */
    int SERVER_ERROR = 9472;

    /**
     * Flag to indicate http request failure (eg. Invoked network request when internet connection is not available)
     */
    int CLIENT_FAIL = 3948;

    /**
     * Flag to indicate http response body is null
     */
    int NO_DATA = 2932;

}
