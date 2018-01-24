package com.invia.challenge.cashpool.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by khayatzadeh on 1/20/2018.
 */

@Entity
@Table(name = "TRAVELER")
public class Traveler extends Auditable<String> implements Serializable {
    private static final long serialVersionUID = -987689979828936913L;

    private Long id;
    @NotBlank
    @Size(min = 2, max = 255)
    private String name;
    @Email
    private String email;
    private Set<TripTravelerRel> tripTravelerRels;

    private Traveler() {
    }

    public Traveler(String name) {
        this.name = name;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(mappedBy = "traveler", cascade = CascadeType.ALL)
    public Set<TripTravelerRel> getTripTravelerRels() {
        return tripTravelerRels;
    }

    public void setTripTravelerRels(Set<TripTravelerRel> tripTravelerRels) {
        this.tripTravelerRels = tripTravelerRels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Traveler traveler = (Traveler) o;

        if (!name.equals(traveler.name)) return false;
        return email != null ? email.equals(traveler.email) : traveler.email == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
