package com.invia.challenge.cashpool.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by khayatzadeh on 1/25/2018.
 */
@Entity
@Table(name = "EXPENSE")
public class Expense extends Auditable<String> {

    private Long id;
    @NotNull
    private BigDecimal amount;
    private TripTravelerRel tripTravelerRel;
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "AMOUNT", nullable = false)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TRIP_TRA_REL_ID", nullable = false)
    public TripTravelerRel getTripTravelerRel() {
        return tripTravelerRel;
    }

    public void setTripTravelerRel(TripTravelerRel tripTravelerRel) {
        this.tripTravelerRel = tripTravelerRel;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
