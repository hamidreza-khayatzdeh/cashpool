package com.invia.challenge.cashpool.service;


import com.invia.challenge.cashpool.exception.CashpoolBaseException;
import com.invia.challenge.cashpool.exception.TripNotFoundException;
import com.invia.challenge.cashpool.model.Expense;
import com.invia.challenge.cashpool.model.Traveler;
import com.invia.challenge.cashpool.model.Trip;
import com.invia.challenge.cashpool.model.TripTravelerRel;
import com.invia.challenge.cashpool.repository.ExpenseRepository;
import com.invia.challenge.cashpool.repository.TripRepository;
import com.invia.challenge.cashpool.repository.TripTravelerRelRepository;
import com.invia.challenge.cashpool.service.dto.Converter;
import com.invia.challenge.cashpool.service.dto.ExpenseDto;
import com.invia.challenge.cashpool.service.dto.TravelerDto;
import com.invia.challenge.cashpool.service.dto.TripDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
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

    @Autowired
    private TravelerService travelerService;

    @Autowired
    private ExpenseRepository expenseRepository;

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

    public TripDto start(TripDto tripDto) throws CashpoolBaseException {
        Trip trip = getTripById(tripDto.getId());
        trip.setStatus(Trip.Status.STARTED);
        trip.setTotalCost(new BigDecimal(0));
        tripRepository.save(trip);
        return getTripDto(trip);
    }

    public TripDto finish(TripDto tripDto) throws CashpoolBaseException {
        Trip trip = getTripById(tripDto.getId());
        trip.setStatus(Trip.Status.FINISHED);
        List<ExpenseDto> expenses = tripDto.getExpenses();
        BigDecimal totalCost = tripDto.getTotalCost() == null ? new BigDecimal(0) : tripDto.getTotalCost();
        for (ExpenseDto expense : expenses)
            totalCost = totalCost.add(expense.getAmount());
        trip.setTotalCost(totalCost);
        BigDecimal share = trip.getTotalCost().divide(new BigDecimal(expenses.size()));
        trip.setShare(share);
        tripRepository.save(trip);
        return getTripDto(trip);
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

    public TripDto getById(Long id) throws CashpoolBaseException {
        Trip trip = getTripById(id);
        return getTripDto(trip);
    }

    private TripDto getTripDto(Trip trip) throws CashpoolBaseException {
        TripDto tripDto = Converter.getTripDto(trip);
        if (!CollectionUtils.isEmpty(trip.getTripTravelerRels())) {
            for (TripTravelerRel tripTravelerRel : trip.getTripTravelerRels()) {
                tripDto.getTravelers().add(Converter.getTravelerDto(tripTravelerRel.getTraveler()));
                tripDto.getExpenses().addAll(getExpenses(tripTravelerRel.getId()));
            }
        }
        return tripDto;
    }

    public TripDto getByLink(String link) throws CashpoolBaseException {
        Trip trip = getTripByLink(link);
        return getTripDto(trip);
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
                () -> new TripNotFoundException(String.format("There is not any Trip with id %s", String.valueOf(id))));
    }

    private Trip getTripByLink(String link) throws CashpoolBaseException {
        return tripRepository.findByLink(link).orElseThrow(
                () -> new TripNotFoundException(String.format("There is not any Trip with link %s", link)));
    }

    public void delete(Long id) throws CashpoolBaseException {
        Trip Trip = getTripById(id);
        try {
            tripRepository.delete(Trip);
        } catch (DataIntegrityViolationException e) {
            throw new CashpoolBaseException(String.format("The Trip with id %s cannot be removed due to unique constraint matters", id));
        }
    }

    public void addExpense(ExpenseDto expenseDto) throws CashpoolBaseException {
        Trip trip = getTripById(expenseDto.getTripId());
        TravelerDto travelerDto = travelerService.get(expenseDto.getTravelerId());
        Traveler traveler = Converter.getTraveler(travelerDto);
        TripTravelerRel tripTravelerRel = tripTravelerRelRepository.findByTripAndTraveler(trip, traveler);
        Expense expense = new Expense();
        expense.setTripTravelerRel(tripTravelerRel);
        expense.setDescription(expenseDto.getDescription());
        expense.setAmount(expenseDto.getAmount());
        expenseRepository.save(expense);
    }


    public List<ExpenseDto> getExpenses(Long tripTravelerRelId) throws CashpoolBaseException {
        TripTravelerRel tripTravelerRel = tripTravelerRelRepository.findById(tripTravelerRelId).orElseThrow(
                () -> new CashpoolBaseException(String.format("The is not TripTravelerRel with id %s", tripTravelerRelId)));
        List<Expense> expenses = tripTravelerRel.getExpenses();
        List<ExpenseDto> expenseDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(expenses)) {
            for (Expense expense : expenses) {
                ExpenseDto expenseDto = Converter.getExpenseDto(tripTravelerRel.getTrip(), tripTravelerRel.getTraveler(), expense);
                expenseDtoList.add(expenseDto);
            }
        }
        return expenseDtoList;
    }
}
