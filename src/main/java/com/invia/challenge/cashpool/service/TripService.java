package com.invia.challenge.cashpool.service;


import com.invia.challenge.cashpool.exception.CashpoolBaseException;
import com.invia.challenge.cashpool.exception.TripNotFoundException;
import com.invia.challenge.cashpool.model.Traveler;
import com.invia.challenge.cashpool.model.Trip;
import com.invia.challenge.cashpool.model.TripTravelerRel;
import com.invia.challenge.cashpool.repository.TripRepository;
import com.invia.challenge.cashpool.repository.TripTravelerRelRepository;
import com.invia.challenge.cashpool.service.dto.Converter;
import com.invia.challenge.cashpool.service.dto.TravelerDto;
import com.invia.challenge.cashpool.service.dto.TripDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by khayatzadeh on 1/23/2018.
 */
@Component
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripTravelerRelRepository tripTravelerRelRepository;

    public void persist(TripDto tripDto) {
        Trip trip = new Trip(tripDto.getName());
        setTripTravelerRels(tripDto, trip);
        tripRepository.save(trip);
    }

    private void setTripTravelerRels(TripDto tripDto, Trip trip) {
        Set<TravelerDto> travelerDtos = tripDto.getTravelers();
        if (!CollectionUtils.isEmpty(travelerDtos)) {
            for (TravelerDto travelerDto : travelerDtos) {
                Traveler traveler = Converter.getTraveler(travelerDto);
                TripTravelerRel tripTravelerRel = new TripTravelerRel(traveler, trip);
                trip.getTripTravelerRels().add(tripTravelerRel);
            }
        }
    }

    public List<TripDto> getTrips() {
        List<Trip> trips = tripRepository.findAll();
        List<TripDto> tripDtos = new ArrayList<>();
        for (Trip trip : trips) {
            TripDto tripDto = Converter.getTripDto(trip);
            tripDtos.add(tripDto);
        }
        return tripDtos;
    }

    public TripDto get(Long id) throws CashpoolBaseException {
        Trip trip = getTripById(id);
        return Converter.getTripDto(trip);
    }

    public TripDto getByLink(String link) {
        Trip trip = getTripByLink(link);
        return Converter.getTripDto(trip);
    }

    @Transactional
    public TripDto update(Long id, TripDto tripDto) throws CashpoolBaseException {
        Trip loadedTrip = getTripById(id);
        loadedTrip.setName(tripDto.getName());
        tripTravelerRelRepository.deleteByTrip(loadedTrip);
        loadedTrip.setTripTravelerRels(new HashSet<>());
        setTripTravelerRels(tripDto, loadedTrip);
        Trip savedTrip = tripRepository.save(loadedTrip);
        return Converter.getTripDto(savedTrip);
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
