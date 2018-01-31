package com.invia.challenge.cashpool.restapi;

import com.invia.challenge.cashpool.exception.CashpoolBaseException;
import com.invia.challenge.cashpool.model.Traveler;
import com.invia.challenge.cashpool.service.TravelerService;
import com.invia.challenge.cashpool.service.dto.TravelerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public void persist(@Valid @RequestBody TravelerDto traveler) {
        travelerService.persist(traveler);
    }

    @GetMapping("/getAll")
    public ResponseEntity<TravelerDto> getAll() {
        List<TravelerDto> travelerDtoList = travelerService.getAll();
        return new ResponseEntity(travelerDtoList, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<TravelerDto> get(@PathVariable(value = "id") Long id) throws CashpoolBaseException {
        TravelerDto traveler = travelerService.get(id);
        return ResponseEntity.ok(traveler);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TravelerDto> update(@PathVariable(value = "id") Long id, @Valid @RequestBody TravelerDto traveler) throws CashpoolBaseException {
        TravelerDto updatedTraveler = travelerService.update(id, traveler);
        return ResponseEntity.ok(updatedTraveler);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<TravelerDto> delete(@PathVariable(value = "id") Long id) throws CashpoolBaseException {
        travelerService.delete(id);
        return ResponseEntity.ok().build();
    }
}
