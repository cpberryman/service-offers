package com.berryman.offers.service.impl;

import com.berryman.offers.dao.OffersRepository;
import com.berryman.offers.exception.*;
import com.berryman.offers.model.Offer;
import com.berryman.offers.timer.OfferExpirationTimer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.berryman.offers.test.util.TestsHelper.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author chris berryman.
 */
@RunWith(MockitoJUnitRunner.class)
public class OffersServiceImplTest {

    @Mock
    private OffersRepository offersRepository;

    @Mock
    private OfferExpirationTimer offersExpirationTimer;

    @InjectMocks
    private OffersServiceImpl offersService;

    @Test
    public void shouldCreateOffer() {
        final Offer offer = stubOffer();
        when(offersRepository.save(offer)).thenReturn(offer);

        Offer expectedOffer = offersService.createOffer(offer);

        assertThat(expectedOffer.getId(), is(TEST_OFFER_ID));
        assertThat(expectedOffer.getPrice(), is(TEST_OFFER_PRICE));
        assertThat(expectedOffer.getCurrency(), is(TEST_OFFER_CURRENCY));
        assertThat(expectedOffer.isExpired(), is(false));
    }

    @Test
    public void shouldFindActiveOffers() {
        when(offersRepository.findAll()).thenReturn(stubOfferList());

        List<Offer> expectedOfferList = offersService.findActiveOffers();

        assertThat(expectedOfferList.size(), is(1));
        assertThat(expectedOfferList.get(0).getId(), is(TEST_OFFER_ID));
        assertThat(expectedOfferList.get(0).getPrice(), is(TEST_OFFER_PRICE));
        assertThat(expectedOfferList.get(0).getCurrency(), is(TEST_OFFER_CURRENCY));
        assertThat(expectedOfferList.get(0).isExpired(), is(false));
    }

    @Test
    public void shouldFindExpiredOffers() {
        when(offersRepository.findAll()).thenReturn(stubOfferList());

        List<Offer> expectedOfferList = offersService.findExpiredOffers();

        assertThat(expectedOfferList.size(), is(1));
        assertThat(expectedOfferList.get(0).getId(), is(ANOTHER_TEST_OFFER_ID));
        assertThat(expectedOfferList.get(0).getPrice(), is(ANOTHER_TEST_OFFER_PRICE));
        assertThat(expectedOfferList.get(0).getCurrency(), is(TEST_OFFER_CURRENCY));
        assertThat(expectedOfferList.get(0).isExpired(), is(true));
    }

    @Test
    public void shouldFindOfferByGivenId() {
        when(offersRepository.findOfferById(TEST_OFFER_ID)).thenReturn(oneElementStubOfferList());

        Offer expectedOffer = offersService.findOfferById(TEST_OFFER_ID);

        assertThat(expectedOffer.getId(), is(TEST_OFFER_ID));
        assertThat(expectedOffer.getPrice(), is(TEST_OFFER_PRICE));
        assertThat(expectedOffer.getCurrency(), is(TEST_OFFER_CURRENCY));
        assertThat(expectedOffer.isExpired(), is(false));
    }

    @Test
    public void shouldFindOffersByGivenPrice() {
        when(offersRepository.findOffersByPrice(TEST_OFFER_PRICE)).thenReturn(oneElementStubOfferList());

        List<Offer> expectedOfferList = offersService.findOffersByPrice(TEST_OFFER_PRICE);

        assertThat(expectedOfferList.size(), is(1));
        assertThat(expectedOfferList.get(0).getId(), is(TEST_OFFER_ID));
        assertThat(expectedOfferList.get(0).getPrice(), is(TEST_OFFER_PRICE));
        assertThat(expectedOfferList.get(0).getCurrency(), is(TEST_OFFER_CURRENCY));
        assertThat(expectedOfferList.get(0).isExpired(), is(false));
    }

    @Test
    public void shouldFindOffersByGivenCurrency() {
        when(offersRepository.findOffersByCurrency(TEST_OFFER_CURRENCY)).thenReturn(oneElementStubOfferList());

        List<Offer> expectedOfferList = offersService.findOffersByCurrency(TEST_OFFER_CURRENCY);

        assertThat(expectedOfferList.size(), is(1));
        assertThat(expectedOfferList.get(0).getId(), is(TEST_OFFER_ID));
        assertThat(expectedOfferList.get(0).getPrice(), is(TEST_OFFER_PRICE));
        assertThat(expectedOfferList.get(0).getCurrency(), is(TEST_OFFER_CURRENCY));
        assertThat(expectedOfferList.get(0).isExpired(), is(false));
    }

    @Test
    public void shouldCancelOffer() {
        when(offersRepository.findOfferById(TEST_OFFER_ID)).thenReturn(oneElementStubOfferList());
        when(offersRepository.save(any(Offer.class))).thenReturn(stubOffer());

        Offer canceledOffer = offersService.cancelOffer(TEST_OFFER_ID);

        assertThat(canceledOffer.isExpired(), is(true));
        verify(offersRepository, times(1)).save(canceledOffer);
    }

    @Test(expected = DuplicateOfferIdException.class)
    public void shouldThrowDuplicateOfferIdExceptionIfOfferExistsInDbWithSameIdAsOfferToAdd() {
        final Offer offer = stubOffer();
        when(offersRepository.findOfferById(TEST_OFFER_ID)).thenReturn(oneElementStubOfferList());
        offersService.createOffer(offer);
    }

    @Test(expected = OfferNotFoundException.class)
    public void shouldThrowOfferNotFoundExceptionIfOfferNotInDb() {
        when(offersRepository.findOfferById(TEST_OFFER_ID)).thenReturn(new ArrayList<>());
        offersService.findOfferById(TEST_OFFER_ID);
    }

    @Test(expected = OfferExpiredException.class)
    public void shouldThrowOfferExpiredExceptionIfOfferExpired() {
        List<Offer> offerList = new ArrayList<>();
        offerList.add(stubExpiredOfferList().get(0));
        when(offersRepository.findOfferById(TEST_OFFER_ID)).thenReturn(offerList);
        offersService.findOfferById(TEST_OFFER_ID);
    }

    @Test(expected = OffersSystemErrorException.class)
    public void shouldThrowOffersSystemErrorExceptionIfMoreThanOneOfferIsReturnedForGivenId() {
        when(offersRepository.findOfferById(TEST_OFFER_ID)).thenReturn(stubOfferList());
        offersService.findOfferById(TEST_OFFER_ID);
    }

    @Test(expected = InvalidCurrencyException.class)
    public void shouldThrowInvalidCurrencyExceptionIfCurrencyNotInSet() {
        offersService.findOffersByCurrency(TEST_OFFER_CURRENCY_INVALID);
    }

    @Test(expected = InvalidDurationTypeException.class)
    public void shouldThrowInvalidDurationExceptionIfDurationNotInSet() {
        final Offer offer = stubOffer();
        offer.setDurationType(TEST_OFFER_DURATION_TYPE_INVALID);
        offersService.createOffer(offer);
    }

}
