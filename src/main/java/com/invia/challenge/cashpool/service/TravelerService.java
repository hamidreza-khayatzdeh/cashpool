package com.invia.challenge.cashpool.service;

import com.invia.challenge.cashpool.exception.CashpoolBaseException;
import com.invia.challenge.cashpool.exception.TravelerNotFoundException;
import com.invia.challenge.cashpool.model.Traveler;
import com.invia.challenge.cashpool.repository.TravelerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by khayatzadeh on 1/23/2018.
 */
@Component
public class TravelerService {

    @Autowired
    private TravelerRepository travelerRepository;

    public void persist(Traveler traveler) {
        travelerRepository.save(traveler);
    }

    public List<Traveler> getAll() {
        return travelerRepository.findAll();
    }

    public Traveler get(Long id) throws CashpoolBaseException {
        return getTravelerById(id);
    }

    public Traveler update(Long id, Traveler traveler) throws CashpoolBaseException {
        Traveler loadedTraveler = getTravelerById(id);
        loadedTraveler.setName(traveler.getName());
        loadedTraveler.setEmail(traveler.getEmail());
        return travelerRepository.save(loadedTraveler);
    }

    private Traveler getTravelerById(Long id) throws CashpoolBaseException {
        return travelerRepository.findById(id).orElseThrow(
                () -> new TravelerNotFoundException("id = ".concat(id.toString())));
    }

    public void delete(Long id) throws CashpoolBaseException {
        Traveler traveler = getTravelerById(id);
        travelerRepository.delete(traveler);
    }
}
