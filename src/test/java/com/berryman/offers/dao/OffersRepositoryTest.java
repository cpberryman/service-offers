package com.berryman.offers.dao;

import com.berryman.offers.model.Offer;
import com.github.fakemongo.Fongo;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.mongodb.Mongo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import com.lordofthejars.nosqlunit.annotation.ShouldMatchDataSet;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Currency;
import java.util.List;
import java.util.Set;

import static com.berryman.offers.test.util.TestsHelper.*;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author chris berryman.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class OffersRepositoryTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private OffersRepository offersRepository;

    @Rule
    public MongoDbRule mongoDbRule = newMongoDbRule().defaultSpringMongoDb(TEST_DB_NAME);

    @Test
    @ShouldMatchDataSet(location = "/testData-offer.json")
    public void shouldSaveAndEchoOffer() {
        offersRepository.save(stubOffer());
    }

    @Test
    @UsingDataSet(locations = "/testData-offer.json")
    public void shouldFindOfferFromGivenId() {
        List<Offer> offerList = offersRepository.findOfferById(TEST_OFFER_ID);

        assertThat(offerList.size(), is(1));
        assertThat(offerList.get(0).getId(), is(TEST_OFFER_ID));
        assertThat(offerList.get(0).getPrice(), is(TEST_OFFER_PRICE));
        assertThat(offerList.get(0).getCurrency(), is(TEST_OFFER_CURRENCY));
        assertThat(offerList.get(0).isExpired(), is(false));


//        Set<Currency> foo =
//                Currency.getAvailableCurrencies().forEach(c -> System.out.println(c));
    }

    @Test
    @UsingDataSet(locations = "/testData-offers.json")
    public void shouldFindOffersFormGivenPrice() {
        List<Offer> offerList = offersRepository.findOffersByPrice(TEST_OFFER_PRICE);

        assertThat(offerList.size(), is(1));
        assertThat(offerList.get(0).getId(), is(TEST_OFFER_ID));
        assertThat(offerList.get(0).getPrice(), is(TEST_OFFER_PRICE));
        assertThat(offerList.get(0).getCurrency(), is(TEST_OFFER_CURRENCY));
        assertThat(offerList.get(0).isExpired(), is(false));
    }

    @Test
    @UsingDataSet(locations = "/testData-offers.json")
    public void shouldFindOffersFormGivenCurrency() {
        List<Offer> offerList = offersRepository.findOffersByCurrency(TEST_OFFER_CURRENCY);

        assertThat(offerList.size(), is(2));
        assertThat(offerList.get(0).getId(), is(TEST_OFFER_ID));
        assertThat(offerList.get(0).getPrice(), is(TEST_OFFER_PRICE));
        assertThat(offerList.get(0).getCurrency(), is(TEST_OFFER_CURRENCY));
        assertThat(offerList.get(0).isExpired(), is(false));
        assertThat(offerList.get(1).getId(), is(ANOTHER_TEST_OFFER_ID));
        assertThat(offerList.get(1).getPrice(), is(ANOTHER_TEST_OFFER_PRICE));
        assertThat(offerList.get(1).getCurrency(), is(TEST_OFFER_CURRENCY));
        assertThat(offerList.get(1).isExpired(), is(true));
    }

    @Configuration
    @EnableMongoRepositories
    @ComponentScan(basePackageClasses = { OffersRepository.class })
    static class TestMongoDbConfiguration extends AbstractMongoConfiguration {

        @Override
        protected String getDatabaseName() {
            return TEST_DB_NAME;
        }

        @Bean
        @Override
        public Mongo mongo() {
            return new Fongo("foo").getMongo();
        }

        @Override
        protected String getMappingBasePackage() {
            return TEST_MAPPING_BASE_PACKAGE_NAME;
        }
    }

}
