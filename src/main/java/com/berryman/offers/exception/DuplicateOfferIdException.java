package com.berryman.offers.exception;

/**
 * @author chris berryman.
 */
public class DuplicateOfferIdException extends RuntimeException {

    public static final String DUPLICATE_OFFER_ERROR = "DUPLICATE_OFFER";

    public DuplicateOfferIdException(final String message) {
        super(message);
    }
}
