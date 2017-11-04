package com.berryman.offers.api;

import com.berryman.offers.configuration.MongoDbConfiguration;
import com.berryman.offers.model.Offer;
import com.berryman.offers.service.OffersService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static com.berryman.offers.test.util.TestsHelper.*;
import static com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author chris berryman.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoDbConfiguration.class)
public class OffersControllerTest {

    private MockMvc mockMvc;

    private OffersService offersService;

    private OffersController offersController;

    @Before
    public void setUp() throws Exception {
        offersController = new OffersController();
        offersService = mock(OffersService.class);
        ReflectionTestUtils.setField(offersController, "offersService", offersService);
        mockMvc = standaloneSetup(offersController).build();
    }

    @Test
    public void shouldCreateOffer() throws Exception {
        final Offer offer = stubOffer();
        when(offersService.createOffer(offer)).thenReturn(offer);

        mockMvc.perform(get("/offers/create")
                .contentType(APPLICATION_JSON)
                .content(testOfferAsJson(offer)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(TEST_OFFER_ID)))
                .andExpect(jsonPath("$.price", is(TEST_OFFER_PRICE)))
                .andExpect(jsonPath("$.currency", is(TEST_OFFER_CURRENCY)))
                .andExpect(jsonPath("$.expired", is(false)));

        verify(offersService, times(1)).createOffer(offer);
        verifyNoMoreInteractions(offersService);
    }

    @Test
    public void shouldGetActiveOffers() throws Exception {
        when(offersService.findActiveOffers()).thenReturn(stubActiveOfferList());

        mockMvc.perform(get("/offers/active"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(TEST_OFFER_ID)))
                .andExpect(jsonPath("$[0].price", is(TEST_OFFER_PRICE)))
                .andExpect(jsonPath("$[0].currency", is(TEST_OFFER_CURRENCY)))
                .andExpect(jsonPath("$[0].expired", is(false)))
                .andExpect(jsonPath("$[1].id", is(ANOTHER_TEST_OFFER_ID)))
                .andExpect(jsonPath("$[1].price", is(ANOTHER_TEST_OFFER_PRICE)))
                .andExpect(jsonPath("$[1].currency", is(TEST_OFFER_CURRENCY)))
                .andExpect(jsonPath("$[1].expired", is(false)));

        verify(offersService, times(1)).findActiveOffers();
        verifyNoMoreInteractions(offersService);
    }

    @Test
    public void shouldGetExpiredOffers() throws Exception {
        when(offersService.findExpiredOffers()).thenReturn(stubExpiredOfferList());

        mockMvc.perform(get("/offers/expired"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(TEST_OFFER_ID)))
                .andExpect(jsonPath("$[0].price", is(TEST_OFFER_PRICE)))
                .andExpect(jsonPath("$[0].currency", is(TEST_OFFER_CURRENCY)))
                .andExpect(jsonPath("$[0].expired", is(true)))
                .andExpect(jsonPath("$[1].id", is(ANOTHER_TEST_OFFER_ID)))
                .andExpect(jsonPath("$[1].price", is(ANOTHER_TEST_OFFER_PRICE)))
                .andExpect(jsonPath("$[1].currency", is(TEST_OFFER_CURRENCY)))
                .andExpect(jsonPath("$[1].expired", is(true)));

        verify(offersService, times(1)).findExpiredOffers();
        verifyNoMoreInteractions(offersService);
    }

    @Test
    public void shouldGetOfferById() throws Exception {
        final Offer offer = stubOffer();
        when(offersService.findOfferById(TEST_OFFER_ID)).thenReturn(offer);

        mockMvc.perform(get("/offers/find/{id}", TEST_OFFER_ID)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(TEST_OFFER_ID)))
                .andExpect(jsonPath("$.price", is(TEST_OFFER_PRICE)))
                .andExpect(jsonPath("$.currency", is(TEST_OFFER_CURRENCY)))
                .andExpect(jsonPath("$.expired", is(false)));

        verify(offersService, times(1)).findOfferById(TEST_OFFER_ID);
        verifyNoMoreInteractions(offersService);
    }

    @Test
    public void shouldGetOffersByPrice() throws Exception {
        when(offersService.findOffersByPrice(anyDouble())).thenReturn(stubSamePricedOfferList());

        mockMvc.perform(get("/offers/price/{price}", TEST_OFFER_PRICE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(TEST_OFFER_ID)))
                .andExpect(jsonPath("$[0].price", is(TEST_OFFER_PRICE)))
                .andExpect(jsonPath("$[0].currency", is(TEST_OFFER_CURRENCY)))
                .andExpect(jsonPath("$[0].expired", is(false)))
                .andExpect(jsonPath("$[1].id", is(ANOTHER_TEST_OFFER_ID)))
                .andExpect(jsonPath("$[1].price", is(TEST_OFFER_PRICE)))
                .andExpect(jsonPath("$[1].currency", is(TEST_OFFER_CURRENCY)))
                .andExpect(jsonPath("$[1].expired", is(true)));

        verify(offersService, times(1)).findOffersByPrice(anyDouble());
        verifyNoMoreInteractions(offersService);
    }

    @Test
    public void shouldGetOffersByCurrency() throws Exception {
        when(offersService.findOffersByCurrency(TEST_OFFER_CURRENCY)).thenReturn(stubActiveOfferList());

        mockMvc.perform(get("/offers/currency/{currency}", TEST_OFFER_CURRENCY))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(TEST_OFFER_ID)))
                .andExpect(jsonPath("$[0].price", is(TEST_OFFER_PRICE)))
                .andExpect(jsonPath("$[0].currency", is(TEST_OFFER_CURRENCY)))
                .andExpect(jsonPath("$[0].expired", is(false)))
                .andExpect(jsonPath("$[1].id", is(ANOTHER_TEST_OFFER_ID)))
                .andExpect(jsonPath("$[1].price", is(ANOTHER_TEST_OFFER_PRICE)))
                .andExpect(jsonPath("$[1].currency", is(TEST_OFFER_CURRENCY)))
                .andExpect(jsonPath("$[1].expired", is(false)));

        verify(offersService, times(1)).findOffersByCurrency(TEST_OFFER_CURRENCY);
        verifyNoMoreInteractions(offersService);
    }

    @Test
    public void shouldCancelOffer() throws Exception {
        final Offer offer = stubOffer();
        offer.setExpired(true);
        when(offersService.cancelOffer(TEST_OFFER_ID)).thenReturn(offer);

        mockMvc.perform(put("/offers/cancel/{id}", TEST_OFFER_ID)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(TEST_OFFER_ID)))
                .andExpect(jsonPath("$.price", is(TEST_OFFER_PRICE)))
                .andExpect(jsonPath("$.currency", is(TEST_OFFER_CURRENCY)))
                .andExpect(jsonPath("$.expired", is(true)));

        verify(offersService, times(1)).cancelOffer(TEST_OFFER_ID);
        verifyNoMoreInteractions(offersService);
    }

}
