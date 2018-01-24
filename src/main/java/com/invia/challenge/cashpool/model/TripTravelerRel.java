package com.invia.challenge.cashpool.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by khayatzadeh on 1/24/2018.
 */
@Entity
@Table(name = "TRIP_TRAVELER_REL")
public class TripTravelerRel extends Auditable<String> implements Serializable {
    private static final long serialVersionUID = 4412272369544644344L;

    private Long id;
    private Traveler traveler;
    private Trip trip;
    private BigDecimal spentAmount;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = Traveler.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "TRAVELER_ID", nullable = false)
    public Traveler getTraveler() {
        return traveler;
    }

    public void setTraveler(Traveler traveler) {
        this.traveler = traveler;
    }

    @ManyToOne(targetEntity = Trip.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "TRIP_ID", nullable = false)
    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @Column(name = "SPENT_AMOUNT")
    public BigDecimal getSpentAmount() {
        return spentAmount;
    }

    public void setSpentAmount(BigDecimal spentAmount) {
        this.spentAmount = spentAmount;
    }
}
