package com.invia.challenge.cashpool.controller;

import com.invia.challenge.cashpool.exception.CashpoolBaseException;
import com.invia.challenge.cashpool.interceptor.Layout;
import com.invia.challenge.cashpool.model.Traveler;
import com.invia.challenge.cashpool.service.TravelerService;
import com.invia.challenge.cashpool.service.dto.TravelerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;
import java.util.ArrayList;
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
    public String showForm(TravelerDto travelerDto, Model model) {
        model.addAttribute("action", "persist");
        model.addAttribute("traveler", travelerDto);
        return "travelerForm";
    }

    @PostMapping("/persist")
    public String persist(@Valid @ModelAttribute TravelerDto travelerDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "persist");
            return "travelerForm";
        }
        travelerService.persist(travelerDto);
        return "redirect:/traveler/list";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute TravelerDto travelerDto, BindingResult bindingResult, Model model) throws CashpoolBaseException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "update");
            return "travelerForm";
        }
        travelerService.update(travelerDto.getId(), travelerDto);
        return "redirect:/traveler/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<TravelerDto> travelers = travelerService.getAll();
        model.addAttribute("travelers", travelers);
        return "travelerList";
    }

    private TravelerDto getTravelerDto(Traveler traveler) {
        TravelerDto travelerDto = new TravelerDto();
        travelerDto.setId(traveler.getId());
        travelerDto.setName(traveler.getName());
        travelerDto.setEmail(traveler.getEmail());
        return travelerDto;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) throws CashpoolBaseException {
        travelerService.delete(id);
        return "redirect:/traveler/list";
    }

    @GetMapping("/get/{id}")
    public String get(@PathVariable(value = "id") Long id, Model model) throws CashpoolBaseException {
        TravelerDto travelerDto = travelerService.get(id);
        model.addAttribute("travelerDto", travelerDto);
        model.addAttribute("action", "update");
        return "travelerForm";
    }

}


