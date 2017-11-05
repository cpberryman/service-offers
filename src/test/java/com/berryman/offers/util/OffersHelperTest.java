package com.berryman.offers.util;

import com.berryman.offers.model.Offer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.berryman.offers.test.util.TestsHelper.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author chris berryman.
 */
@RunWith(MockitoJUnitRunner.class)
public class OffersHelperTest {

    private OffersHelper offersHelper = new OffersHelper();

    @Test
    public void shouldCalculateSecondsForMonth(){
        final Offer offer = stubOffer();

        Long seconds = offersHelper.calculateSeconds(offer);

        assertThat(seconds, is(EXPECTED_SECONDS_FOR_MONTH));
    }

    @Test
    public void shouldCalculateSecondsForDay( ){
        final Offer offer = stubOfferWithDayDuration();

        Long seconds = offersHelper.calculateSeconds(offer);

        assertThat(seconds, is(EXPECTED_SECONDS_FOR_DAY));
    }

}
