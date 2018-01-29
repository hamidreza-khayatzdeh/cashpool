package com.invia.challenge.cashpool.controller;

import com.invia.challenge.cashpool.exception.CashpoolBaseException;
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
        tripService.update(tripDto.getId(), tripDto);
        return "redirect:/trip/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<TripDto> tripDtos = tripService.getTrips();
        model.addAttribute("trips", tripDtos);
        return "tripList";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) throws CashpoolBaseException {
        tripService.delete(id);
        return "redirect:/trip/list";
    }

    @GetMapping("/get/{id}")
    public String get(@PathVariable(value = "id") Long id, Model model) throws CashpoolBaseException {
        TripDto tripDto = tripService.get(id);
        List<TravelerDto> travelers = travelerService.getAll();
        model.addAttribute("allTravelers", travelers);
        model.addAttribute("tripDto", tripDto);
        model.addAttribute("action", "update");
        return "tripForm";
    }

    @GetMapping("/link/{link}")
    public String link(@PathVariable(value = "link") String link, Model model) throws CashpoolBaseException {
        TripDto tripDto = tripService.getByLink(link);
        model.addAttribute("expenseDto", Converter.getExpenseDto(tripDto));
        return "tripLink";
    }

    @GetMapping("/expense/{id}")
    public String expense(@PathVariable(value = "id") Long id, Model model) throws CashpoolBaseException {
        TripDto tripDto = tripService.get(id);
        model.addAttribute("expenseDto", Converter.getExpenseDto(tripDto));
        model.addAttribute("tripDto", tripDto);
        return "tripLink";
    }

    @PostMapping("/addExpense")
    public String addExpense(@Valid @ModelAttribute ExpenseDto expenseDto, BindingResult bindingResult, Model model) throws CashpoolBaseException {
        if (bindingResult.hasErrors()) {
            TripDto tripDto = tripService.get(expenseDto.getTripId());
            expenseDto.setAllTravelers(tripDto.getTravelers());
            expenseDto.setTripName(tripDto.getName());
            model.addAttribute("tripDto", tripDto);
            return "tripLink";
        }
        tripService.addExpense(expenseDto);
        return "redirect:/trip/list";
    }


}
