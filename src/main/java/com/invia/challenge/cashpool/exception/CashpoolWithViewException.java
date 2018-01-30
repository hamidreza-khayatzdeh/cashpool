package com.invia.challenge.cashpool.exception;

/**
 * Created by khayatzadeh on 1/30/2018.
 */
public class CashpoolWithViewException extends CashpoolBaseException {

    public static final String Error404 = "404";
    public static final String ErrorPage = "error";

    public CashpoolWithViewException(String viewName, String message, Throwable cause) {
        super(viewName, message, cause);
    }
}
