package com.berryman.offers.exception;

/**
 * @author chris berryman.
 */
public class OffersSystemErrorException extends RuntimeException {

    public static final String SYSTEM_ERROR = "OFFERS_API_SYSTEM_ERROR";

    public OffersSystemErrorException(final String message) {
        super(message);
    }
}
