package com.invia.challenge.cashpool.service.dto;

import com.invia.challenge.cashpool.model.Trip;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by khayatzadeh on 1/25/2018.
 */
public class TripDto implements Serializable {

    private static final String NAME_PATTERN = "^[a-zA-Z\\\\s]+";

    private Long id;
    @NotBlank
    @Size(min = 2, max = 255)
    @Pattern(regexp = NAME_PATTERN)
    private String name;
    private String link;
    private Trip.Status status;
    private BigDecimal totalCost;
    private Set<TravelerDto> travelers = new HashSet<>();

    public TripDto() {
    }

    public TripDto(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Trip.Status getStatus() {
        return status;
    }

    public void setStatus(Trip.Status status) {
        this.status = status;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Set<TravelerDto> getTravelers() {
        return travelers;
    }

    public void setTravelers(Set<TravelerDto> travelers) {
        this.travelers = travelers;
    }
}
