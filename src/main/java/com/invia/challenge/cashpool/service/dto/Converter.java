package com.invia.challenge.cashpool.service.dto;

import com.invia.challenge.cashpool.model.Expense;
import com.invia.challenge.cashpool.model.Traveler;
import com.invia.challenge.cashpool.model.Trip;
import com.invia.challenge.cashpool.model.TripTravelerRel;
import com.invia.challenge.cashpool.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by khayatzadeh on 1/27/2018.
 */

public class Converter {

    public static Traveler getTraveler(TravelerDto travelerDto) {
        Traveler traveler = new Traveler(travelerDto.getName());
        traveler.setEmail(travelerDto.getEmail());
        traveler.setId(travelerDto.getId());
        return traveler;
    }

    public static TravelerDto getTravelerDto(Traveler traveler) {
        TravelerDto travelerDto = new TravelerDto(traveler.getName());
        travelerDto.setEmail(traveler.getEmail());
        travelerDto.setId(traveler.getId());
        return travelerDto;
    }

    public static TripDto getTripDto(Trip trip) {
        TripDto tripDto = new TripDto();
        tripDto.setId(trip.getId());
        tripDto.setName(trip.getName());
        tripDto.setLink(trip.getLink());
        tripDto.setStatus(trip.getStatus());
        tripDto.setTotalCost(trip.getTotalCost());
        return tripDto;
    }

    public static ExpenseDto getExpenseDto(TripDto tripDto) {
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setAllTravelers(tripDto.getTravelers());
        expenseDto.setTripId(tripDto.getId());
        expenseDto.setTripName(tripDto.getName());
        return expenseDto;
    }

    public static ExpenseDto getExpenseDto(Trip trip, Traveler traveler, Expense expense) {
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setTravelerName(traveler.getName());
        expenseDto.setAmount(expense.getAmount());
        expenseDto.setDescription(expense.getDescription());
        expenseDto.setTripName(trip.getName());
        expenseDto.setTravelerId(traveler.getId());
        expenseDto.setTripId(trip.getId());
        return expenseDto;
    }
}
