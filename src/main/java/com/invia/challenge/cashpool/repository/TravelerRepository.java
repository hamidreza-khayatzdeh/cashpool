package com.invia.challenge.cashpool.repository;

import com.invia.challenge.cashpool.model.Traveler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by khayatzadeh on 1/20/2018.
 */
public interface TravelerRepository extends JpaRepository<Traveler, Long> {
    Optional<Traveler> findById(Long id);
}
