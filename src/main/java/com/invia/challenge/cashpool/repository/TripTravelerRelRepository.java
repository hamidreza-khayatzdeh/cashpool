package com.invia.challenge.cashpool.repository;

import com.invia.challenge.cashpool.model.Traveler;
import com.invia.challenge.cashpool.model.Trip;
import com.invia.challenge.cashpool.model.TripTravelerRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by khayatzadeh on 1/25/2018.
 */
public interface TripTravelerRelRepository extends JpaRepository<TripTravelerRel, Long> {

    void deleteByTrip(Trip trip);

}
