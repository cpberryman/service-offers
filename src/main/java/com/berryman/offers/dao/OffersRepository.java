package com.berryman.offers.dao;

import com.berryman.offers.model.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author chris berryman.
 */
public interface OffersRepository extends MongoRepository<Offer, String> {

    List<Offer> findOfferById(final String id);

    List<Offer> findOffersByPrice(final double price);

    List<Offer> findOffersByCurrency(final String currency);

}
