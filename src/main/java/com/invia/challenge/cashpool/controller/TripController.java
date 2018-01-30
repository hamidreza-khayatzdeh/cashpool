package com.invia.challenge.cashpool.controller;

import com.invia.challenge.cashpool.exception.CashpoolBaseException;
import com.invia.challenge.cashpool.exception.CashpoolWithViewException;
import com.invia.challenge.cashpool.exception.TripNotFoundException;
import com.invia.challenge.cashpool.interceptor.Layout;
import com.invia.challenge.cashpool.service.TravelerService;
import com.invia.challenge.cashpool.service.TripService;
import com.invia.challenge.cashpool.service.dto.Converter;
import com.invia.challenge.cashpool.service.dto.ExpenseDto;
import com.invia.challenge.cashpool.service.dto.TravelerDto;
import com.invia.challenge.cashpool.service.dto.TripDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by khayatzadeh on 1/24/2018.
 */
@Controller
@RequestMapping("/trip")
@Layout(value = "/layouts/default")
public class TripController extends WebMvcConfigurerAdapter {

    @Autowired
    private TripService tripService;

    @Autowired
    private TravelerService travelerService;

    @GetMapping("")
    public String index() {
        return "redirect:/trip/list";
    }

    @GetMapping("/persist")
    public String showForm(TripDto tripDto, Model model) {
        List<TravelerDto> travelers = travelerService.getAll();
        model.addAttribute("action", "persist");
        model.addAttribute("tripDto", tripDto);
        model.addAttribute("allTravelers", travelers);
        return "tripForm";
    }

    @PostMapping("/persist")
    public String persist(@Valid @ModelAttribute TripDto tripDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<TravelerDto> travelers = travelerService.getAll();
            model.addAttribute("action", "persist");
            model.addAttribute("allTravelers", travelers);
            return "tripForm";
        }
        tripService.persist(tripDto);
        return "redirect:/trip/list";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute TripDto tripDto, BindingResult bindingResult, Model model) throws CashpoolBaseException {
        if (bindingResult.hasErrors()) {
            List<TravelerDto> travelers = travelerService.getAll();
            model.addAttribute("action", "update");
            model.addAttribute("allTravelers", travelers);
            return "tripForm";
        }
        try {
            tripService.update(tripDto.getId(), tripDto);
            return "redirect:/trip/list";
        } catch (TripNotFoundException e) {
            throw new CashpoolWithViewException(CashpoolWithViewException.Error404, e.getMessage(), e.getCause());
        }
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<TripDto> tripDtos = tripService.getTrips();
        model.addAttribute("trips", tripDtos);
        return "tripList";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) throws CashpoolBaseException {
        try {
            tripService.delete(id);
            return "redirect:/trip/list";
        } catch (TripNotFoundException e) {
            throw new CashpoolWithViewException(CashpoolWithViewException.Error404, e.getMessage(), e.getCause());
        } catch (CashpoolBaseException e) {
            throw new CashpoolWithViewException(CashpoolWithViewException.ErrorPage, e.getMessage(), e.getCause());
        }
    }

    @GetMapping("/get/{id}")
    public String get(@PathVariable(value = "id") Long id, Model model) throws CashpoolBaseException {
        TripDto tripDto = null;
        try {
            tripDto = tripService.getById(id);
            List<TravelerDto> travelers = travelerService.getAll();
            model.addAttribute("allTravelers", travelers);
            model.addAttribute("tripDto", tripDto);
            model.addAttribute("action", "update");
            return "tripForm";
        } catch (TripNotFoundException e) {
            throw new CashpoolWithViewException(CashpoolWithViewException.Error404, e.getMessage(), e.getCause());
        }
    }

    @GetMapping("/link/{link}")
    public String link(@PathVariable(value = "link") String link, Model model) throws CashpoolBaseException {
        try {
            TripDto tripDto = tripService.getByLink(link);
            model.addAttribute("expenseDto", Converter.getExpenseDto(tripDto));
            model.addAttribute("tripDto", tripDto);
            return "tripLink";
        } catch (TripNotFoundException e) {
            throw new CashpoolWithViewException(CashpoolWithViewException.Error404, e.getMessage(), e.getCause());
        }
    }

    @GetMapping("/expense/{id}")
    public String expense(@PathVariable(value = "id") Long id, Model model) throws CashpoolBaseException {
        TripDto tripDto = tripService.getById(id);
        model.addAttribute("expenseDto", Converter.getExpenseDto(tripDto));
        model.addAttribute("tripDto", tripDto);
        return "tripLink";
    }

    @PostMapping("/addExpense")
    public String addExpense(@Valid @ModelAttribute ExpenseDto expenseDto, BindingResult bindingResult, Model model) throws CashpoolBaseException {
        if (bindingResult.hasErrors()) {
            TripDto tripDto = tripService.getById(expenseDto.getTripId());
            expenseDto.setAllTravelers(tripDto.getTravelers());
            expenseDto.setTripName(tripDto.getName());
            model.addAttribute("tripDto", tripDto);
            return "tripLink";
        }
        tripService.addExpense(expenseDto);
        return "redirect:/trip/list";
    }


}
