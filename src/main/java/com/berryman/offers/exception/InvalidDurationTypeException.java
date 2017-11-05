package com.berryman.offers.exception;

/**
 * @author chris berryman.
 */
public class InvalidDurationTypeException extends RuntimeException {

    public static final String INVALID_DURATION_TYPE_ERROR = "INVALID_DURATION_TYPE";

    public InvalidDurationTypeException(final String message) {
        super(message);
    }
}
