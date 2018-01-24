package com.invia.challenge.cashpool.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

/**
 * Created by khayatzadeh on 1/23/2018.
 */
@Entity
@Table(name = "TRIP")
public class Trip extends Auditable<String> implements Serializable {
    private static final long serialVersionUID = -8102803221454380692L;

    private Trip() {
    }

    public Trip(String name) {
        this.name = name;
    }

    public enum Status {
        STARTED('s'),
        FINISHED('f');

        private Character value;

        Status(Character val) {
            this.value = val;
        }

        public Character value(){
            return this.value;
        }
    }

    private Long id;
    @NotBlank
    @Size(min = 2, max = 255)
    private String name;
    private String link;
    private Status status;
    private BigDecimal totalCost;
    private BigDecimal share;
    private Set<TripTravelerRel> tripTravelerRels;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NAME", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "LINK", nullable = false, unique = true)
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Column(name = "STATUS", nullable = false)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(name = "TOTAL_COST")
    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    @Column(name = "SHARE")
    public BigDecimal getShare() {
        return share;
    }

    public void setShare(BigDecimal share) {
        this.share = share;
    }

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    public Set<TripTravelerRel> getTripTravelerRels() {
        return tripTravelerRels;
    }

    public void setTripTravelerRels(Set<TripTravelerRel> tripTravelerRels) {
        this.tripTravelerRels = tripTravelerRels;
    }

    @PrePersist
    public void prePersist() {
        UUID uuid = UUID.randomUUID();
        this.setLink(this.getName().replaceAll("\\s", "")+ "_" + String.format("%s",uuid.toString()));
        this.setStatus(Status.STARTED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trip trip = (Trip) o;

        if (!id.equals(trip.id)) return false;
        if (!name.equals(trip.name)) return false;
        if (!link.equals(trip.link)) return false;
        if (status != trip.status) return false;
        if (!totalCost.equals(trip.totalCost)) return false;
        return share.equals(trip.share);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + link.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + totalCost.hashCode();
        result = 31 * result + share.hashCode();
        return result;
    }
}
