package com.berryman.offers.dao;

import com.berryman.offers.model.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author chris berryman.
 */
public interface OffersRepository extends MongoRepository<Offer, String> {

    List<Offer> findOfferById(String id);

    List<Offer> findOffersByPrice(double price);

    List<Offer> findOffersByCurrency(String currency);

}
