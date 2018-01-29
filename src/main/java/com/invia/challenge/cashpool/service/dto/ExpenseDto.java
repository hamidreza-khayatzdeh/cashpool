package com.invia.challenge.cashpool.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by khayatzadeh on 1/29/2018.
 */
public class ExpenseDto implements Serializable {

    private Long tripId;
    private String tripName;
    private Set<TravelerDto> allTravelers;
    private Long travelerId;
    private String travelerName;
    @NotNull
    private BigDecimal amount;
    private String description;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public Set<TravelerDto> getAllTravelers() {
        return allTravelers;
    }

    public void setAllTravelers(Set<TravelerDto> allTravelers) {
        this.allTravelers = allTravelers;
    }

    public Long getTravelerId() {
        return travelerId;
    }

    public void setTravelerId(Long travelerId) {
        this.travelerId = travelerId;
    }

    public String getTravelerName() {
        return travelerName;
    }

    public void setTravelerName(String travelerName) {
        this.travelerName = travelerName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
