package com.berryman.offers.configuration;

import com.berryman.offers.dao.OffersRepository;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;

/**
 * @author chris berryman.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.berryman.offers.dao")
@ComponentScan(basePackageClasses = { OffersRepository.class })
public class MongoDbConfiguration extends AbstractMongoConfiguration {

    @Value("${database.name}")
    private String databaseName;

    @Value("${database.host.name}")
    private String databaseHostName;

    @Value("${mapping.base.package.name}")
    private String mappingBasePackageName;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    public Mongo mongo() throws UnknownHostException {
        return new MongoClient(databaseHostName);
    }

    @Override
    protected String getMappingBasePackage() {
        return mappingBasePackageName;
    }

}
