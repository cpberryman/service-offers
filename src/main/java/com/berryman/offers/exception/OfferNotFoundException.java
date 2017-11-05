package com.berryman.offers.exception;

/**
 * @author chris berryman.
 */
public class OfferNotFoundException extends RuntimeException {

    public static final String OFFER_NOT_FOUND_ERROR = "OFFER_NOT_FOUND";

    public OfferNotFoundException(final String message) {
        super(message);
    }
}
