package com.invia.challenge.cashpool.repository;

import com.invia.challenge.cashpool.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by khayatzadeh on 1/23/2018.
 */
public interface TripRepository extends JpaRepository<Trip, Long> {

    Optional<Trip> findById(Long id);

    Optional<Trip> findByLink(String link);
}
