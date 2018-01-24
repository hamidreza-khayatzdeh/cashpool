package com.invia.challenge.cashpool.restapi;

import com.invia.challenge.cashpool.exception.CashpoolBaseException;
import com.invia.challenge.cashpool.model.Traveler;
import com.invia.challenge.cashpool.service.TravelerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by khayatzadeh on 1/23/2018.
 */
@RestController
@RequestMapping("/rest-api/traveler")
public class TravelerRestWebService {

    @Autowired
    private TravelerService travelerService;

    @PostMapping("/persist")
    public void persist(@Valid @RequestBody Traveler traveler) {
        travelerService.persist(traveler);
    }

    @GetMapping("/getAll")
    public List<Traveler> getAll() {
        return travelerService.getAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Traveler> get(@PathVariable(value = "id") Long id) throws CashpoolBaseException {
        Traveler traveler = travelerService.get(id);
        return ResponseEntity.ok(traveler);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Traveler> update(@PathVariable(value = "id") Long id, @Valid @RequestBody Traveler traveler) throws CashpoolBaseException {
        Traveler updatedTraveler = travelerService.update(id, traveler);
        return ResponseEntity.ok(updatedTraveler);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Traveler> delete(@PathVariable(value = "id") Long id) throws CashpoolBaseException {
        travelerService.delete(id);
        return ResponseEntity.ok().build();
    }
}
