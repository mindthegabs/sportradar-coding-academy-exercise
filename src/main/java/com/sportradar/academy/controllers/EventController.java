package com.sportradar.academy.controllers;

import com.sportradar.academy.dto.EventDTO;
import com.sportradar.academy.exception.PersistenceException;
import com.sportradar.academy.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.lang.invoke.MethodHandles;

@Controller
@RequestMapping()
public class EventController {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Autowired
    private EventService simpleEventService;

    //shows event table with all existing events
    @GetMapping("events")
    public String showTable(@RequestParam Boolean orderedBySport,Model model){
        try {
            model.addAttribute("events", simpleEventService.getEvents(orderedBySport));
        } catch (PersistenceException e) {
            LOG.error("Persistence Exception thrown during getEvents()", e);
        }
        return "event-table";
    }

    //shows form to add a new event
    @GetMapping ("/events/add")
    public String showCreateEventForm(Model model) {
        EventDTO event = new EventDTO();
        model.addAttribute("event", event);
        return "add-event-form";
    }

    //adds a new event
    @PostMapping("/events")
    public String submitCreateEventForm(@ModelAttribute("event") EventDTO event) {
        try {
            simpleEventService.createEvent(event);
        } catch (PersistenceException e) {
            LOG.error("PersistenceException thrown during createEvent()", e);
        }
        return "redirect:/events?orderedBySport=false";

    }


}
