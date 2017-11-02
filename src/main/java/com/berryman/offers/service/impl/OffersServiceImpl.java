package com.berryman.offers.service.impl;

import com.berryman.offers.dao.OffersRepository;
import com.berryman.offers.exception.InvalidCurrencyException;
import com.berryman.offers.model.Offer;
import com.berryman.offers.service.OffersService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chris berryman.
 */
public class OffersServiceImpl implements OffersService {

    @Autowired
    private OffersRepository offersRepository;

    @Override
    public Offer createOffer(Offer offer) {
        return offersRepository.save(offer);
    }

    @Override
    public List<Offer> findActiveOffers() {
        return offersRepository
                .findAll()
                .stream()
                .filter(o -> !o.isExpired())
                .collect(Collectors.toList());
    }

    @Override
    public List<Offer> findExpiredOffers() {
        return offersRepository
                .findAll()
                .stream()
                .filter(o -> o.isExpired())
                .collect(Collectors.toList());
    }

    @Override
    public Offer findOfferById(String id) {
        List<Offer> offerList = offersRepository.findOfferById(id);
        validateOfferList(offerList);
        return offerList.get(0);
    }

    @Override
    public List<Offer> findOffersByPrice(double price) {
        return offersRepository.findOffersByPrice(price);
    }

    @Override
    public List<Offer> findOffersByCurrency(String currency) {
        if(isValidCurrency(currency)) {
            return offersRepository.findOffersByCurrency(currency);
        } else {
            throw new InvalidCurrencyException();
        }
    }

    /*lists with offer found by id should only have one element*/
    private void validateOfferList(List<Offer> offerList) {
        if(offerList.size() > 1) {
            throw new RuntimeException();
        }
    }

    private boolean isValidCurrency(String currency) {
        return Currency.getAvailableCurrencies().stream().filter(c -> c.toString().equals(currency)).count() > 0;
    }
}
