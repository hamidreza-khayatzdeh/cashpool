package com.invia.challenge.cashpool.service.dto;

import com.invia.challenge.cashpool.model.Expense;
import com.invia.challenge.cashpool.model.Traveler;
import com.invia.challenge.cashpool.model.Trip;
import com.invia.challenge.cashpool.model.TripTravelerRel;

import java.math.BigDecimal;

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
        return getTravelerDto(traveler, travelerDto);
    }

    private static TravelerDto getTravelerDto(Traveler traveler, TravelerDto travelerDto) {
        travelerDto.setEmail(traveler.getEmail());
        travelerDto.setId(traveler.getId());
        return travelerDto;
    }

    public static TravelerDto getTravelerDto(TripDto tripDto, TripTravelerRel tripTravelerRel) {
        Traveler traveler = tripTravelerRel.getTraveler();
        TravelerDto travelerDto = new TravelerDto(traveler.getName());
        getTravelerDto(traveler, travelerDto);
        BigDecimal totalSpentAmount = tripTravelerRel.getTotalSpentAmount() != null ? tripTravelerRel.getTotalSpentAmount() : BigDecimal.ZERO;
        travelerDto.setTotalSpentAmount(totalSpentAmount);
        BigDecimal share = tripDto.getShare();
        if (share != null)
            travelerDto.setPaymentAmount(share.subtract(totalSpentAmount));
        return travelerDto;
    }

    public static TripDto getTripDto(Trip trip) {
        TripDto tripDto = new TripDto();
        tripDto.setId(trip.getId());
        tripDto.setName(trip.getName());
        tripDto.setLink(trip.getLink());
        tripDto.setStatus(trip.getStatus());
        tripDto.setShare(trip.getShare());
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
