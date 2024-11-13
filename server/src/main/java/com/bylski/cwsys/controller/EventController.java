package com.bylski.cwsys.controller;

import com.bylski.cwsys.model.Event;
import com.bylski.cwsys.model.dto.EventDTO;
import com.bylski.cwsys.service.inf.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/all")
    public Page<EventDTO> getAllEvents(Pageable pageable){
        return eventService.getAllEvents(pageable);
    }

    @GetMapping("/{eventId}")
    public EventDTO getEventById(@PathVariable Long eventId){
        return eventService.getEventById(eventId);
    }

    @PostMapping("/new")
    public void addNewEvent(@RequestBody Event event){
        eventService.addEvent(event);
    }

    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable Long eventId){
        eventService.deleteEvent(eventId);
    }

    @PatchMapping("/add-coach")
    public void addCoachToEvent(@RequestParam Long eventId, @RequestParam Long coachId){
        eventService.addCoach(eventId, coachId);
    }

    @PatchMapping("/remove-coach")
    public void removeCoachFromEvent(@RequestParam Long eventId, @RequestParam Long coachId){
        eventService.removeCoach(eventId, coachId);
    }


}
