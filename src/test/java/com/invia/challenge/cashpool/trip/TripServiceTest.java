package com.invia.challenge.cashpool.trip;

import com.invia.challenge.cashpool.CashpoolApplicationTest;
import com.invia.challenge.cashpool.model.Traveler;
import com.invia.challenge.cashpool.model.Trip;
import com.invia.challenge.cashpool.model.TripTravelerRel;
import com.invia.challenge.cashpool.service.TravelerService;
import com.invia.challenge.cashpool.service.TripService;
import com.invia.challenge.cashpool.service.dto.Converter;
import com.invia.challenge.cashpool.service.dto.TravelerDto;
import com.invia.challenge.cashpool.service.dto.TripDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.persistence.Convert;
import java.util.HashSet;
import java.util.List;

/**
 * Created by khayatzadeh on 1/24/2018.
 */
public class TripServiceTest extends CashpoolApplicationTest {

    @Autowired
    private TripService tripService;

    @Autowired
    private TravelerService travelerService;

    private TripDto trip;

    @Before
    public void init() {
        trip = new TripDto("Germany - Leipzig");
        TravelerDto traveler = new TravelerDto("Hamidreza");
        traveler.setEmail("hamidreza.bidgoli@gmail.com");
        travelerService.persist(traveler);
        trip.getTravelers().add(traveler);
        tripService.persist(trip);
    }

    @Test
    public void getTripsTest() {
        List<TripDto> trips = tripService.getTrips();
        for (TripDto trip : trips) {
            Assert.notEmpty(trip.getTravelers(), "There is no traveler!!!");
        }
        Assert.notEmpty(trips, "There is no trip!!!");
    }

    @Test
    public void getTripByLinkTest() {
        TripDto loadedTrip = tripService.getByLink(trip.getLink());
        Assert.isTrue(loadedTrip.getLink().equals(trip.getLink()), "The loaded trip's link is not equal to persisted one");
    }
}
