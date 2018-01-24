package com.invia.challenge.cashpool.controller;

import com.invia.challenge.cashpool.exception.CashpoolBaseException;
import com.invia.challenge.cashpool.interceptor.Layout;
import com.invia.challenge.cashpool.model.Traveler;
import com.invia.challenge.cashpool.service.TravelerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by khayatzadeh on 1/20/2018.
 */
@Controller
@RequestMapping("/traveler")
@Layout(value = "layouts/default")
public class TravelerController extends WebMvcConfigurerAdapter {

    @Autowired
    private TravelerService travelerService;

    @GetMapping("")
    public String index(){
        return "redirect:/traveler/list";
    }

    @GetMapping("/persist")
    public String showForm(Traveler traveler, Model model) {
        model.addAttribute("action", "persist");
        return "travelerForm";
    }

    @PostMapping("/persist")
    public String persist(@Valid @ModelAttribute Traveler traveler, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "travelerForm";
        travelerService.persist(traveler);
        return "redirect:/traveler/list";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute Traveler traveler, BindingResult bindingResult) throws CashpoolBaseException {
        if (bindingResult.hasErrors())
            return "travelerForm";
        travelerService.update(traveler.getId(), traveler);
        return "redirect:/traveler/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Traveler> travelers = travelerService.getAll();
        model.addAttribute("travelers", travelers);
        return "travelerList";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) throws CashpoolBaseException {
        travelerService.delete(id);
        return "redirect:/traveler/list";
    }

    @GetMapping("/get/{id}")
    public String get(@PathVariable(value = "id") Long id, Model model) throws CashpoolBaseException {
        Traveler traveler = travelerService.get(id);
        model.addAttribute("traveler", traveler);
        model.addAttribute("action", "update");
        return "travelerForm";
    }

}


