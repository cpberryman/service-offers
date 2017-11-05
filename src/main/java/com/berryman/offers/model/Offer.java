package com.berryman.offers.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author chris berryman.
 */
@Document(collection = "offer")
public class Offer {

    @Id
    private String id;
    private String currency;
    private double price;
    private boolean expired;
    private String durationType;
    private int durationNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public String getDurationType() {
        return durationType;
    }

    public void setDurationType(String durationType) {
        this.durationType = durationType;
    }

    public int getDurationNumber() {
        return durationNumber;
    }

    public void setDurationNumber(int durationNumber) {
        this.durationNumber = durationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Offer offer = (Offer) o;

        return new EqualsBuilder()
                .append(price, offer.price)
                .append(expired, offer.expired)
                .append(id, offer.id)
                .append(currency, offer.currency)
                .append(durationType, offer.durationType)
                .append(durationNumber, offer.durationNumber)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(currency)
                .append(price)
                .append(expired)
                .append(durationType)
                .append(durationNumber)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id='" + id + '\'' +
                ", currency='" + currency + '\'' +
                ", price=" + price +
                ", expired=" + expired +
                ", durationType='" + durationType + '\'' +
                ", durationNumber='" + durationNumber + '\'' +
                '}';
    }

}
