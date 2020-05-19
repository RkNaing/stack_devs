package com.rk.stackdevs.data;

/**
 * Created by Rahul Kumar on 09/05/2020.
 *
 * Exception class for demonstration of data request failure
 **/
public final class DataRequestException extends Exception {

    private static final int UNDEFINED = 228;

    private final int type;

    /**
     * @param message Error Message
     */
    public DataRequestException(String message) {
        super(message);
        this.type = UNDEFINED;
    }

    /**
     * @param message Error Message
     * @param type Any integer flag to be used to categorise DataRequestException
     */
    public DataRequestException(String message, int type) {
        super(message);
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "DataRequestException{" +
                "message=" + getMessage() +
                ", type=" + type +
                '}';
    }
}
