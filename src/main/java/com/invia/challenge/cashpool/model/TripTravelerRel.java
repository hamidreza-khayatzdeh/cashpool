package com.invia.challenge.cashpool.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by khayatzadeh on 1/24/2018.
 */
@Entity
@Table(name = "TRIP_TRAVELER_REL")
public class TripTravelerRel extends Auditable<String> {

    private Long id;
    private Traveler traveler;
    private Trip trip;
    private BigDecimal totalSpentAmount;
    private String description;
    private List<Expense> expenses;

    private TripTravelerRel() {
    }

    public TripTravelerRel(Traveler traveler, Trip trip) {
        this.traveler = traveler;
        this.trip = trip;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = Traveler.class)
    @JoinColumn(name = "TRAVELER_ID", nullable = false)
    public Traveler getTraveler() {
        return traveler;
    }

    public void setTraveler(Traveler traveler) {
        this.traveler = traveler;
    }

    @ManyToOne(targetEntity = Trip.class)
    @JoinColumn(name = "TRIP_ID", nullable = false)
    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @Column(name = "TOTAL_SPENT_AMOUNT")
    public BigDecimal getTotalSpentAmount() {
        return totalSpentAmount;
    }

    public void setTotalSpentAmount(BigDecimal totalSpentAmount) {
        this.totalSpentAmount = totalSpentAmount;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "tripTravelerRel")
    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
