package com.berryman.offers.timer;

import com.berryman.offers.dao.OffersRepository;
import com.berryman.offers.model.Offer;
import com.berryman.offers.util.OffersHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static com.berryman.offers.test.util.TestsHelper.*;
import static org.mockito.Mockito.*;

/**
 * @author chris berryman.
 */
@RunWith(MockitoJUnitRunner.class)
public class OfferExpirationTimerTest {

    @Mock
    private OffersRepository offersRepository;

    @Mock
    private OffersHelper offersHelper;

    @InjectMocks
    private OfferExpirationTimer offerExpirationTimer;

    @Test
    public void shouldUpdateOfferWithTrueFlagForIsExpiredWhenTimerReachesZero() throws Exception {
        final Offer offer = spy(Offer.class);
        when(offersHelper.calculateSeconds(any(Offer.class))).thenReturn(TEST_SECONDS_DURATION_FOR_DAY);

        offerExpirationTimer.countdownAndExpireOffer(offer);
        Thread.sleep(DELAY_TO_ALLOW_RUN_METHOD_TO_EXECUTE);

        verify(offer, times(1)).setExpired(true);
        verify(offersRepository, times(1)).save(any(Offer.class));
        verifyNoMoreInteractions(offersRepository);
        verify(offerExpirationTimer, times(1)).countdownAndExpireOffer(offer);
        verifyNoMoreInteractions(offerExpirationTimer);
    }

    @Test
    public void shouldCountdownForMoreThanOneOfferConcurrently() throws Exception {
        final Offer offer = spy(Offer.class);
        final Offer offer1 = spy(Offer.class);
        when(offersHelper.calculateSeconds(any(Offer.class))).thenReturn(TEST_SECONDS_DURATION_FOR_DAY);
        OfferExpirationTimer offerExpirationTimer1 = new OfferExpirationTimer();
        ReflectionTestUtils.setField(offerExpirationTimer1, "offersHelper", offersHelper);
        ReflectionTestUtils.setField(offerExpirationTimer1, "offersRepository", offersRepository);

        offerExpirationTimer.countdownAndExpireOffer(offer);
        offerExpirationTimer1.countdownAndExpireOffer(offer1);
        Thread.sleep(DELAY_TO_ALLOW_RUN_METHOD_TO_EXECUTE);

        verify(offer, times(1)).setExpired(true);
        verify(offer1, times(1)).setExpired(true);
        verify(offersRepository, times(2)).save(any(Offer.class));
        verifyNoMoreInteractions(offersRepository);
    }

}
