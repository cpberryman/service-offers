package com.berryman.offers.service;

import com.berryman.offers.model.Offer;

import java.util.List;

/**
 * @author chris berryman.
 */
public interface OffersService {

    Offer createOffer(final Offer offer);

    List<Offer> findActiveOffers();

    List<Offer> findExpiredOffers();

    Offer findOfferById(final String id);

    List<Offer> findOffersByPrice(final double price);

    List<Offer> findOffersByCurrency(final String currency);

    Offer cancelOffer(final String id);

}
