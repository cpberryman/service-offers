package com.berryman.offers.util;

import com.berryman.offers.model.Offer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chris berryman.
 */
@Component
public class OffersHelper {

    public static final String OFFER_NOT_FOUND_ERROR_MESSAGE = "Offer not found in db with id: ";
    public static final String OFFER_EXPIRED_ERROR_MESSAGE = "Offer is expired with id: ";
    public static final String INVALID_CURRENCY_ERROR_MESSAGE = "Offer has invalid currency: ";
    public static final String INVALID_DURATION_ERROR_MESSAGE = "Offer has invalid duration: ";
    public static final String DUPLICATE_OFFER_ID_ERROR_MESSAGE = "Offer already exists with id: ";
    public static final String JSON_PROCESSING_ERROR_MESSAGE = "Could not process json string";

    public static Set<String> durationTypes;

    static {
        durationTypes = new HashSet<>();
        durationTypes.add("MONTH");
        durationTypes.add("DAY");
    }

    public Long calculateSeconds(Offer offer) {
        return offer.getDurationType().equals("MONTH") ?
                calculateSecondsForMonths(offer)
                : offer.getDurationType().equals("DAY") ?
                calculateSecondsForDays(offer)
                : 0;
    }

    private static Long calculateSecondsForMonths(Offer offer) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime futureDate = LocalDateTime.now().plusMonths(offer.getDurationNumber());
        Duration duration = Duration.between(today, futureDate);
        return duration.getSeconds();
    }

    private static Long calculateSecondsForDays(Offer offer) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime futureDate = LocalDateTime.now().plusDays(offer.getDurationNumber());
        Duration duration = Duration.between(today, futureDate);
        return duration.getSeconds();
    }

}
