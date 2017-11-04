package com.berryman.offers.api;

import com.berryman.offers.model.Offer;
import com.berryman.offers.service.OffersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chris berryman.
 */
@RestController
@RequestMapping("/offers")
public class OffersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OffersController.class);

    @Autowired
    private OffersService offersService;

    @RequestMapping(value = "/create", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Offer> create(@RequestBody final Offer offer) {
        return new ResponseEntity<>(offersService.createOffer(offer), HttpStatus.OK);
    }

    @RequestMapping(value = "/active", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Offer>> getActiveOffers() {
        return new ResponseEntity<>(offersService.findActiveOffers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/expired", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Offer>> getExpiredOffers() {
        return new ResponseEntity<>(offersService.findExpiredOffers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Offer> getOfferById(@PathVariable final String id) {
        return new ResponseEntity<>(offersService.findOfferById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/price/{price}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Offer>> getOffersByPrice(@PathVariable final double price) {
        return new ResponseEntity<>(offersService.findOffersByPrice(price), HttpStatus.OK);
    }

    @RequestMapping(value = "/currency/{currency}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Offer>> getOffersByCurrency(@PathVariable final String currency) {
        return new ResponseEntity<>(offersService.findOffersByCurrency(currency), HttpStatus.OK);
    }

    @RequestMapping(value = "/cancel/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Offer> cancelOffer(@PathVariable final String id) {
        return new ResponseEntity<>(offersService.cancelOffer(id), HttpStatus.OK);
    }

}
