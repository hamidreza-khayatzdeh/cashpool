package com.invia.challenge.cashpool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by khayatzadeh on 1/21/2018.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TripNotFoundException extends RuntimeException {

    public TripNotFoundException(String msg) {
        super(String.format("Could not find trip with %s ", msg));
    }
}
