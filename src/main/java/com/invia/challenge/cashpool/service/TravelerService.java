package com.invia.challenge.cashpool.service;

import com.invia.challenge.cashpool.exception.CashpoolBaseException;
import com.invia.challenge.cashpool.exception.TravelerNotFoundException;
import com.invia.challenge.cashpool.model.Traveler;
import com.invia.challenge.cashpool.repository.TravelerRepository;
import com.invia.challenge.cashpool.service.dto.Converter;
import com.invia.challenge.cashpool.service.dto.TravelerDto;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by khayatzadeh on 1/23/2018.
 */
@Component
public class TravelerService {

    @Autowired
    private TravelerRepository travelerRepository;

    public void persist(TravelerDto travelerDto) {
        Traveler traveler = Converter.getTraveler(travelerDto);
        travelerRepository.save(traveler);
    }

    public List<TravelerDto> getAll() {
        List<Traveler> travelers = travelerRepository.findAll();
        return travelers.stream().map(Converter::getTravelerDto).collect(Collectors.toList());
    }

    public TravelerDto get(Long id) throws CashpoolBaseException {
        Traveler traveler = getTravelerById(id);
        return Converter.getTravelerDto(traveler);
    }

    public TravelerDto update(Long id, TravelerDto travelerDto) throws CashpoolBaseException {
        Traveler loadedTraveler = getTravelerById(id);
        loadedTraveler.setName(travelerDto.getName());
        loadedTraveler.setEmail(travelerDto.getEmail());
        travelerRepository.save(loadedTraveler);
        return Converter.getTravelerDto(loadedTraveler);
    }

    private Traveler getTravelerById(Long id) throws CashpoolBaseException {
        return travelerRepository.findById(id).orElseThrow(
                () -> new TravelerNotFoundException(String.format("The Traveler with Id %s not found", id)));
    }

    public void delete(Long id) throws CashpoolBaseException {
        Traveler traveler = getTravelerById(id);
        try {
            travelerRepository.delete(traveler);
        } catch (DataIntegrityViolationException e) {
            throw new CashpoolBaseException(String.format("The Traveler with id %s cannot be removed due to unique constraint matters", id));
        }
    }

}
