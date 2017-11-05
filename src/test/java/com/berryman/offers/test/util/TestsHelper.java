package com.berryman.offers.test.util;

import com.berryman.offers.model.Offer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chris berryman.
 */
public final class TestsHelper {

    public static final String TEST_DB_NAME = "test-offers-db";
    public static final String TEST_MAPPING_BASE_PACKAGE_NAME = "com.berryman.offers.dao";
    public static final String TEST_OFFER_ID = "1";
    public static final String ANOTHER_TEST_OFFER_ID = "3";
    public static final String TEST_OFFER_CURRENCY = "JPY";
    public static final String TEST_OFFER_CURRENCY_INVALID = "foo!";
    public static final double TEST_OFFER_PRICE = 18.50;
    public static final double ANOTHER_TEST_OFFER_PRICE = 22.00;
    public static final String TEST_OFFER_DURATION_TYPE_MONTH = "MONTH";
    public static final String TEST_OFFER_DURATION_TYPE_DAY = "DAY";
    public static final int TEST_OFFER_DURATION_NUMBER_FOR_MONTH = 1;
    public static final int TEST_OFFER_DURATION_NUMBER_FOR_DAY = 2;
    public static final String TEST_OFFER_DURATION_TYPE_INVALID = "bar!";
    public static final Long EXPECTED_SECONDS_FOR_MONTH = 2592000L;
    public static final Long EXPECTED_SECONDS_FOR_DAY = 172800L;
    public static final Long TEST_SECONDS_DURATION_FOR_DAY = 2000L;
    public static final Long DELAY_TO_ALLOW_RUN_METHOD_TO_EXECUTE = 3000L;

    public static final MediaType APPLICATION_JSON = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype());

    private TestsHelper() {
        //util class
    }

    public static Offer stubOffer() {
        final Offer offer = new Offer();
        offer.setId(TEST_OFFER_ID);
        offer.setPrice(TEST_OFFER_PRICE);
        offer.setCurrency(TEST_OFFER_CURRENCY);
        offer.setExpired(false);
        offer.setDurationType(TEST_OFFER_DURATION_TYPE_MONTH);
        offer.setDurationNumber(TEST_OFFER_DURATION_NUMBER_FOR_MONTH);
        return offer;
    }

    public static Offer stubOfferWithDayDuration() {
        final Offer offer = new Offer();
        offer.setId(TEST_OFFER_ID);
        offer.setPrice(TEST_OFFER_PRICE);
        offer.setCurrency(TEST_OFFER_CURRENCY);
        offer.setExpired(false);
        offer.setDurationType(TEST_OFFER_DURATION_TYPE_DAY);
        offer.setDurationNumber(TEST_OFFER_DURATION_NUMBER_FOR_DAY);
        return offer;
    }

    public static List<Offer> stubOfferList() {
        List<Offer> offerList = new ArrayList<>();
        final Offer offer = new Offer();
        offer.setId(ANOTHER_TEST_OFFER_ID);
        offer.setPrice(ANOTHER_TEST_OFFER_PRICE);
        offer.setCurrency(TEST_OFFER_CURRENCY);
        offer.setExpired(true);
        offerList.add(offer);
        offerList.add(stubOffer());
        return offerList;
    }

    public static List<Offer> oneElementStubOfferList() {
        List<Offer> offerList = new ArrayList<>();
        offerList.add(stubOffer());
        return offerList;
    }

    public static List<Offer> stubActiveOfferList() {
        List<Offer> offerList = oneElementStubOfferList();
        final Offer offer = new Offer();
        offer.setId(ANOTHER_TEST_OFFER_ID);
        offer.setPrice(ANOTHER_TEST_OFFER_PRICE);
        offer.setCurrency(TEST_OFFER_CURRENCY);
        offer.setExpired(false);
        offerList.add(offer);
        return offerList;
    }

    public static List<Offer> stubExpiredOfferList() {
        List<Offer> offerList = new ArrayList<>();
        final Offer offer = new Offer();
        offer.setId(TEST_OFFER_ID);
        offer.setPrice(TEST_OFFER_PRICE);
        offer.setCurrency(TEST_OFFER_CURRENCY);
        offer.setExpired(true);
        offerList.add(offer);
        final Offer offer1 = new Offer();
        offer1.setId(ANOTHER_TEST_OFFER_ID);
        offer1.setPrice(ANOTHER_TEST_OFFER_PRICE);
        offer1.setCurrency(TEST_OFFER_CURRENCY);
        offer1.setExpired(true);
        offerList.add(offer1);
        return offerList;
    }

    public static List<Offer> stubSamePricedOfferList() {
        List<Offer> offerList = new ArrayList<>();
        final Offer offer = new Offer();
        offer.setId(TEST_OFFER_ID);
        offer.setPrice(TEST_OFFER_PRICE);
        offer.setCurrency(TEST_OFFER_CURRENCY);
        offer.setExpired(false);
        offerList.add(offer);
        final Offer offer1 = new Offer();
        offer1.setId(ANOTHER_TEST_OFFER_ID);
        offer1.setPrice(TEST_OFFER_PRICE);
        offer1.setCurrency(TEST_OFFER_CURRENCY);
        offer1.setExpired(true);
        offerList.add(offer1);
        return offerList;
    }

    public static String testOfferAsJson(Offer offer) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(offer);
    }

}
