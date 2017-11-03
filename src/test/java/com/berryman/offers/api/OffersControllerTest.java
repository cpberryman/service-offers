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

import static com.berryman.offers.test.util.TestsHelper.APPLICATION_JSON;
import static com.berryman.offers.test.util.TestsHelper.stubOffer;
import static com.berryman.offers.test.util.TestsHelper.testOfferAsJson;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.price", is(18.50)))
                .andExpect(jsonPath("$.currency", is("JPY")))
                .andExpect(jsonPath("$.expired", is(false)));

        verify(offersService, times(1)).createOffer(offer);
        verifyNoMoreInteractions(offersService);
    }

    @Test
    public void shouldGetActiveOffers() throws Exception {

    }

    @Test
    public void shouldGetExpiredOffers() throws Exception {

    }

    @Test
    public void shouldGetOfferById() throws Exception {

    }

    @Test
    public void shouldGetOffersByPrice() throws Exception {

    }

    @Test
    public void shouldGetOffersByCurrency() throws Exception {

    }

    @Test
    public void shouldCancelOffer() throws Exception {

    }

}
