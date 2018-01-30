package com.invia.challenge.cashpool.exception;

/**
 * Created by khayatzadeh on 1/23/2018.
 */
public class CashpoolBaseException extends Exception {

    private String viewName;

    public String getViewName() {
        return viewName;
    }

    public CashpoolBaseException() {
    }

    public CashpoolBaseException(String viewName, String message, Throwable cause) {
        super(message, cause);
        this.viewName = viewName;
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
