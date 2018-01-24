package com.invia.challenge.cashpool.service;


import com.invia.challenge.cashpool.exception.CashpoolBaseException;
import com.invia.challenge.cashpool.exception.TripNotFoundException;
import com.invia.challenge.cashpool.model.Trip;
import com.invia.challenge.cashpool.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * Created by khayatzadeh on 1/23/2018.
 */
@Component
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Transactional
    public void persist(Trip trip) {
        tripRepository.save(trip);
    }

    @Transactional
    public List<Trip> getAll() {
        return tripRepository.findAll();
    }

    public Trip get(Long id) throws CashpoolBaseException {
        return getTripById(id);
    }

    public Trip getByLink(String link) {
        return getTripByLink(link);
    }

    public Trip update(Long id, Trip Trip) throws CashpoolBaseException {
        Trip loadedTrip = getTripById(id);
        loadedTrip.setName(Trip.getName());
        return tripRepository.save(loadedTrip);
    }

    private Trip getTripById(Long id) throws CashpoolBaseException {
        return tripRepository.findById(id).orElseThrow(
                () -> new TripNotFoundException("id = ".concat(id.toString())));
    }

    private Trip getTripByLink(String link) {
        return tripRepository.findByLink(link).orElseThrow(
                () -> new TripNotFoundException("link = ".concat(link)));
    }

    public void delete(Long id) throws CashpoolBaseException {
        Trip Trip = getTripById(id);
        tripRepository.delete(Trip);
    }
}
