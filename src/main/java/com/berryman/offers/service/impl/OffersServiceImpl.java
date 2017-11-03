package com.berryman.offers.service.impl;

import com.berryman.offers.dao.OffersRepository;
import com.berryman.offers.exception.InvalidCurrencyException;
import com.berryman.offers.exception.OffersSystemErrorException;
import com.berryman.offers.model.Offer;
import com.berryman.offers.service.OffersService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Currency.getAvailableCurrencies;

/**
 * @author chris berryman.
 */
public class OffersServiceImpl implements OffersService {

    @Autowired
    private OffersRepository offersRepository;

    @Override
    public Offer createOffer(final Offer offer) {
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
    public List<Offer> findOffersByPrice(final double price) {
        return offersRepository.findOffersByPrice(price);
    }

    @Override
    public List<Offer> findOffersByCurrency(String currency) {
        if(isValidCurrency(currency)) {
            return offersRepository.findOffersByCurrency(currency);
        } else {
            throw new InvalidCurrencyException(InvalidCurrencyException.INVALID_CURRENCY_ERROR);
        }
    }

    @Override
    public Offer cancelOffer(final String id) {
        List<Offer> offersTemp = offersRepository.findOfferById(id);
        validateOfferList(offersTemp);
        Offer offerToCancel = offersTemp.get(0);
        offerToCancel.setExpired(true);
        offersRepository.save(offerToCancel);
        return offerToCancel;
    }

    /*lists with offer found by id should only have one element*/
    private void validateOfferList(final List<Offer> offerList) {
        if(offerList.size() > 1) {
            throw new OffersSystemErrorException(OffersSystemErrorException.SYSTEM_ERROR);
        }
    }

    private boolean isValidCurrency(final String currency) {
        return getAvailableCurrencies()
                .stream()
                .filter(c -> c.toString().equals(currency))
                .count() > 0;
    }

}
