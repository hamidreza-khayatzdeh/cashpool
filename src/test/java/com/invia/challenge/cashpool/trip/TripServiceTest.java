package com.invia.challenge.cashpool.trip;

import com.invia.challenge.cashpool.CashpoolApplicationTest;
import com.invia.challenge.cashpool.exception.CashpoolBaseException;
import com.invia.challenge.cashpool.service.TravelerService;
import com.invia.challenge.cashpool.service.TripService;
import com.invia.challenge.cashpool.service.dto.TravelerDto;
import com.invia.challenge.cashpool.service.dto.TripDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

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
        Long travelerId = travelerService.persist(traveler);
        traveler.setId(travelerId);
        Assert.notNull(traveler.getId(), "The traveler must be persist");
        trip.getTravelers().add(traveler);
        Long tripId = tripService.persist(trip);
        trip.setId(tripId);
        Assert.notNull(trip.getId(), "The trip must be persist");
    }

    @Test
    public void getTripsTest() {
        List<TripDto> trips = tripService.getTrips();
        Assert.notEmpty(trips, "There is no traveler!!!");
    }

    @Test
    public void getTripByLinkTest() throws CashpoolBaseException {
        TripDto loadedTrip = tripService.getById(trip.getId());
        TripDto loadedTripByLink = tripService.getByLink(loadedTrip.getLink());
        Assert.isTrue(loadedTrip.getLink().equals(loadedTripByLink.getLink()), "The loaded trip's link is not equal to persisted one");
    }
}
