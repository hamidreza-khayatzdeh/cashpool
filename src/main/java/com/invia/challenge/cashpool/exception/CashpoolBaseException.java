package com.invia.challenge.cashpool.exception;

/**
 * Created by khayatzadeh on 1/23/2018.
 */
public class CashpoolBaseException extends Exception {

    public CashpoolBaseException() {
    }

    public CashpoolBaseException(String message) {
        super(message);
    }

    public CashpoolBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CashpoolBaseException(Throwable cause) {
        super(cause);
    }

    public CashpoolBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
