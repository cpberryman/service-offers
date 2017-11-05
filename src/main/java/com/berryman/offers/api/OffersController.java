package com.berryman.offers.api;

import com.berryman.offers.exception.*;
import com.berryman.offers.model.Offer;
import com.berryman.offers.service.OffersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.berryman.offers.util.OffersHelper.*;

/**
 * @author chris berryman.
 */
@RestController
@RequestMapping("/offers")
public class OffersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OffersController.class);

    @Autowired
    private OffersService offersService;

    //TODO controller advice for errors
    @RequestMapping(value = "/create", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity create(@RequestBody final Offer offer) {
        try {
            return new ResponseEntity<>(offersService.createOffer(offer), HttpStatus.OK);
        } catch (InvalidCurrencyException e) {
            return new ResponseEntity<>(asJsonString(INVALID_CURRENCY_ERROR_MESSAGE + offer.getCurrency()), HttpStatus.OK);
        } catch (InvalidDurationTypeException e) {
            return new ResponseEntity<>(asJsonString(INVALID_DURATION_ERROR_MESSAGE + offer.getDurationType()), HttpStatus.OK);
        } catch (DuplicateOfferIdException e) {
            return new ResponseEntity<>(asJsonString(DUPLICATE_OFFER_ID_ERROR_MESSAGE + offer.getId()), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/active", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Offer>> getActiveOffers() {
        return new ResponseEntity<>(offersService.findActiveOffers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/expired", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Offer>> getExpiredOffers() {
        return new ResponseEntity<>(offersService.findExpiredOffers(), HttpStatus.OK);
    }

    //TODO controller advice for errors
    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getOfferById(@PathVariable final String id) {
        try {
            return new ResponseEntity<>(offersService.findOfferById(id), HttpStatus.OK);
        } catch (OfferNotFoundException e) {
            return new ResponseEntity<>(asJsonString(OFFER_NOT_FOUND_ERROR_MESSAGE + id), HttpStatus.OK);
        } catch (OfferExpiredException e) {
            return new ResponseEntity<>(asJsonString(OFFER_EXPIRED_ERROR_MESSAGE + id), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/price/{price}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Offer>> getOffersByPrice(@PathVariable final double price) {
        return new ResponseEntity<>(offersService.findOffersByPrice(price), HttpStatus.OK);
    }

    @RequestMapping(value = "/currency/{currency}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Offer>> getOffersByCurrency(@PathVariable final String currency) {
        return new ResponseEntity<>(offersService.findOffersByCurrency(currency), HttpStatus.OK);
    }

    @RequestMapping(value = "/cancel/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Offer> cancelOffer(@PathVariable final String id) {
        return new ResponseEntity<>(offersService.cancelOffer(id), HttpStatus.OK);
    }

    private String asJsonString(String message) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            LOGGER.error(JSON_PROCESSING_ERROR_MESSAGE);
        }
        return jsonString;
    }

}
