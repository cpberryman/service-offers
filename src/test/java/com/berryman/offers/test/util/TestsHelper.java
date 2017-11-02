package com.berryman.offers.test.util;

import com.berryman.offers.model.Offer;

/**
 * @author chris berryman.
 */
public final class TestsHelper {

    public static final String TEST_DB_NAME = "test-offers-db";
    public static final String TEST_MAPPING_BASE_PACKAGE_NAME = "com.berryman.offers.dao";
    public static final String TEST_OFFER_ID = "1";
    public static final String ANOTHER_TEST_OFFER_ID = "3";
    public static final String TEST_OFFER_CURRENCY = "JPY";
    public static final double TEST_OFFER_PRICE = 18.50;
    public static final double ANOTHER_TEST_OFFER_PRICE = 22.00;

    public static Offer stubOffer() {
        final Offer offer = new Offer();
        offer.setId(TEST_OFFER_ID);
        offer.setPrice(TEST_OFFER_PRICE);
        offer.setCurrency(TEST_OFFER_CURRENCY);
        offer.setExpired(false);
        return offer;
    }
}
