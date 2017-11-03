package com.berryman.offers.exception;

/**
 * @author chris berryman.
 */
public class OfferExpiredException extends RuntimeException {

    public static final String OFFER_EXPIRED_ERROR = "OFFER_EXPIRED";

    public OfferExpiredException(final String message) {
        super(message);
    }
}
