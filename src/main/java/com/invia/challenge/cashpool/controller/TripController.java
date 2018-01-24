package com.invia.challenge.cashpool.controller;

import com.invia.challenge.cashpool.exception.CashpoolBaseException;
import com.invia.challenge.cashpool.interceptor.Layout;
import com.invia.challenge.cashpool.model.Trip;
import com.invia.challenge.cashpool.service.TripService;
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

    @GetMapping("")
    public String index(){
        return "redirect:/trip/list";
    }

    @GetMapping("/persist")
    public String showForm(Trip trip, Model model) {
        model.addAttribute("action", "persist");
        return "tripForm";
    }

    @PostMapping("/persist")
    public String persist(@Valid @ModelAttribute Trip trip, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "tripForm";
        tripService.persist(trip);
        return "redirect:/trip/list";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute Trip trip, BindingResult bindingResult) throws CashpoolBaseException {
        if (bindingResult.hasErrors())
            return "tripForm";
        tripService.update(trip.getId(), trip);
        return "redirect:/trip/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Trip> trips = tripService.getAll();
        model.addAttribute("trips", trips);
        return "tripList";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) throws CashpoolBaseException {
        tripService.delete(id);
        return "redirect:/trip/list";
    }

    @GetMapping("/get/{id}")
    public String get(@PathVariable(value = "id") Long id, Model model) throws CashpoolBaseException {
        Trip trip = tripService.get(id);
        model.addAttribute("trip", trip);
        model.addAttribute("action", "update");
        return "tripForm";
    }

    @GetMapping("/link/{link}")
    public String link(@PathVariable(value = "link") String link, Model model) throws CashpoolBaseException {
        Trip trip = tripService.getByLink(link);
        model.addAttribute("trip", trip);
        model.addAttribute("action", "update");
        return "tripLink";
    }
}
