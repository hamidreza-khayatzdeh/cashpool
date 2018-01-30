package com.invia.challenge.cashpool.controller;

import com.invia.challenge.cashpool.exception.CashpoolBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by khayatzadeh on 1/30/2018.
 */
@ControllerAdvice(basePackages = "com.invia.challenge.cashpool")
public class ExceptionController {

    private static Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(CashpoolBaseException.class)
    public String cashpoolBaseException(CashpoolBaseException ex, Model model) {
        logger.debug(ex.getMessage(), ex);
        if (ex.getViewName() != null) {
            model.addAttribute("errorMsg", ex.getMessage());
            return ex.getViewName();
        } else {
            return ex.getMessage();
        }
    }
}
