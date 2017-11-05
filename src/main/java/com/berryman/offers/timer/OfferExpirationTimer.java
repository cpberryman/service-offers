package com.berryman.offers.timer;

import com.berryman.offers.dao.OffersRepository;
import com.berryman.offers.model.Offer;
import com.berryman.offers.util.OffersHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author chris berryman.
 */
@Component
@Scope("prototype")
public class OfferExpirationTimer extends Thread {

    @Autowired
    private OffersRepository offersRepository;

    @Autowired
    private OffersHelper offersHelper;


    public void countdownAndExpireOffer(Offer offer) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                offer.setExpired(true);
                offersRepository.save(offer);
            }
        }, offersHelper.calculateSeconds(offer));
    }

}
