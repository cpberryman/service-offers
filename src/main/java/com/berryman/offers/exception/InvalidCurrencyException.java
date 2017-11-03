package com.berryman.offers.exception;

/**
 * @author chris berryman.
 */
public class InvalidCurrencyException extends RuntimeException {

    public static final String INVALID_CURRENCY_ERROR = "INVALID_CURRENCY";

    public InvalidCurrencyException(final String message) {
        super(message);
    }
}
