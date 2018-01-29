package com.invia.challenge.cashpool.expense;

import com.invia.challenge.cashpool.CashpoolApplicationTest;
import com.invia.challenge.cashpool.model.Traveler;
import com.invia.challenge.cashpool.model.Trip;
import com.invia.challenge.cashpool.model.TripTravelerRel;
import com.invia.challenge.cashpool.repository.ExpenseRepository;
import com.invia.challenge.cashpool.service.TravelerService;
import com.invia.challenge.cashpool.service.TripService;
import com.invia.challenge.cashpool.service.dto.TravelerDto;
import com.invia.challenge.cashpool.service.dto.TripDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by khayatzadeh on 1/25/2018.
 */
public class ExpenseRepositoryTest extends CashpoolApplicationTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private TravelerService travelerService;

    @Autowired
    private TripService tripService;

    private List<Traveler> travelers;

    @Before
    public void init() {
        TravelerDto hamidreza = new TravelerDto("Hamidreza");
        hamidreza.setEmail("hamidreza.bidgoli@gmail.com");

        TravelerDto hendrik = new TravelerDto("Hendrik");
        hendrik.setEmail("hendrik@invia.du");

        TripDto tripToGermany = new TripDto();
        tripToGermany.setName("Germany - Leipzig");

        tripService.persist(tripToGermany);
    }

    @Test
    public void getTotalSpentAmountTest() {

    }
}
