package com.invia.challenge.cashpool.trip;

import com.invia.challenge.cashpool.CashpoolApplicationTest;
import com.invia.challenge.cashpool.model.Traveler;
import com.invia.challenge.cashpool.model.Trip;
import com.invia.challenge.cashpool.model.TripTravelerRel;
import com.invia.challenge.cashpool.service.TravelerService;
import com.invia.challenge.cashpool.service.TripService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.math.BigDecimal;
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

    private Trip trip;

    @Before
    public void init() {
        trip = new Trip("Germany - Leipzig");
        Traveler traveler = new Traveler("Hamidreza");
        traveler.setEmail("hamidreza.bidgoli@gmail.com");
        travelerService.persist(traveler);

        HashSet<TripTravelerRel> tripTravelerRels = new HashSet<>();
        TripTravelerRel tripTravelerRel = new TripTravelerRel();
        tripTravelerRel.setTraveler(traveler);
        tripTravelerRel.setTrip(trip);
        tripTravelerRel.setSpentAmount(BigDecimal.TEN);
        tripTravelerRels.add(tripTravelerRel);

        trip.setTripTravelerRels(tripTravelerRels);
        tripService.persist(trip);
    }

    @Test
    public void getTripsTest() {
        List<Trip> trips = tripService.getAll();
        for (Trip trip : trips) {
            Assert.notEmpty(trip.getTripTravelerRels(), "There is no traveler!!!");
        }
        Assert.notEmpty(trips, "There is no trip!!!");
    }

    @Test
    public void getTripByLinkTest() {
        Trip loadedTrip = tripService.getByLink(trip.getLink());
        Assert.isTrue(loadedTrip.getLink().equals(trip.getLink()), "The loaded trip's link is not equal to persisted one");
    }
}
