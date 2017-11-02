package com.berryman.offers.configuration;

import com.berryman.offers.dao.OffersRepository;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;

/**
 * @author chris berryman.
 */
//@Configuration
//@EnableMongoRepositories//(basePackages = "com.berryman.offers")
//@ComponentScan(basePackageClasses = { OffersRepository.class })
public class MongoDbConfiguration extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "foo";
    }

    @Override
    public Mongo mongo() throws UnknownHostException {
        return new MongoClient("localhost:27017");
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.berryman.offers.dao";
    }
}
