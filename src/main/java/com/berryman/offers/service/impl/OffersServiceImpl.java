package com.berryman.offers.service.impl;

import com.berryman.offers.dao.OffersRepository;
import com.berryman.offers.exception.*;
import com.berryman.offers.model.Offer;
import com.berryman.offers.service.OffersService;
import com.berryman.offers.timer.OfferExpirationTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.berryman.offers.util.OffersHelper.OFFER_EXPIRED_ERROR_MESSAGE;
import static com.berryman.offers.util.OffersHelper.OFFER_NOT_FOUND_ERROR_MESSAGE;
import static com.berryman.offers.util.OffersHelper.durationTypes;
import static java.util.Currency.getAvailableCurrencies;

/**
 * @author chris berryman.
 */
@Service
public class OffersServiceImpl implements OffersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OffersServiceImpl.class);

    @Autowired
    private OffersRepository offersRepository;

    @Autowired
    private OfferExpirationTimer offerExpirationTimer;

    @Override
    public Offer createOffer(final Offer offer) {
        validateOffer(offer);
        if(offersRepository.findOfferById(offer.getId()).isEmpty()) {
            offerExpirationTimer.countdownAndExpireOffer(offer);
            return offersRepository.save(offer);
        } else {
            LOGGER.warn();
            throw new DuplicateOfferIdException();
        }

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
        if(!offerList.isEmpty()) {
            if(!offerList.get(0).isExpired()) {
                return offerList.get(0);
            } else {
                LOGGER.warn(OFFER_EXPIRED_ERROR_MESSAGE + id);
                throw new OfferExpiredException(OFFER_EXPIRED_ERROR_MESSAGE + id);
            }
        } else {
            LOGGER.warn(OFFER_NOT_FOUND_ERROR_MESSAGE + id);
            throw new OfferNotFoundException(OFFER_NOT_FOUND_ERROR_MESSAGE + id);
        }

    }

    @Override
    public List<Offer> findOffersByPrice(final double price) {
        return offersRepository.findOffersByPrice(price);
    }

    @Override
    public List<Offer> findOffersByCurrency(String currency) {
        if (isValidCurrency(currency)) {
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

    private void validateOffer(Offer offer) {
        if (!isValidCurrency(offer.getCurrency())) {
            LOGGER.warn(OFFER_EXPIRED_ERROR_MESSAGE);
            throw new InvalidCurrencyException(InvalidCurrencyException.INVALID_CURRENCY_ERROR);
        }

        if (!isValidDuration(offer.getDurationType())) {
            throw new InvalidDurationTypeException(InvalidDurationTypeException.INVALID_DURATION_TYPE_ERROR);
        }
    }

    private boolean isValidDuration(String durationType) {
        return durationTypes
                .stream()
                .filter(d -> d.equals(durationType))
                .count() > 0;
    }

    /*lists with offer found by id should only have one element*/
    private void validateOfferList(final List<Offer> offerList) {
        if (offerList.size() > 1) {
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
