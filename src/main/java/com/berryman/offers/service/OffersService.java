package com.berryman.offers.service;

import com.berryman.offers.model.Offer;

import java.util.List;

/**
 * @author chris berryman.
 */
public interface OffersService {

    Offer createOffer(Offer offer);

    List<Offer> findActiveOffers();

    List<Offer> findExpiredOffers();

    Offer findOfferById(String id);

    List<Offer> findOffersByPrice(double price);

    List<Offer> findOffersByCurrency(String currency);

}
