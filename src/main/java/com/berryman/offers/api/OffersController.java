package com.berryman.offers.api;

import com.berryman.offers.model.Offer;
import com.berryman.offers.service.OffersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chris berryman.
 */
@RestController
@RequestMapping("/offers")
public class OffersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OffersController.class);

    @Autowired
    private OffersService offersService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Offer> create(@RequestBody Offer offer) {


        // TODO: call persistence layer to update
        return new ResponseEntity<>(offer, HttpStatus.OK);
    }



}
