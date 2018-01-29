package com.invia.challenge.cashpool.service.dto;

import com.invia.challenge.cashpool.model.Traveler;
import com.invia.challenge.cashpool.model.Trip;
import com.invia.challenge.cashpool.model.TripTravelerRel;
import org.springframework.util.CollectionUtils;

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
        Set<TripTravelerRel> tripTravelerRels = trip.getTripTravelerRels();
        if (!CollectionUtils.isEmpty(tripTravelerRels)) {
            for (TripTravelerRel tripTravelerRel : tripTravelerRels)
                tripDto.getTravelers().add(getTravelerDto(tripTravelerRel.getTraveler()));
        }
        return tripDto;
    }
}
