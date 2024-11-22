package com.bylski.cwsys.controller;

import com.bylski.cwsys.model.Event;
import com.bylski.cwsys.model.dto.EventDTO;
import com.bylski.cwsys.model.payload.EventPayload;
import com.bylski.cwsys.service.inf.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.annotations.NotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@Tag(name = "Event Controller", description = "Methods for Event API")
@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(
            summary = "Get all events",
            description = "Returns Page object containing all events, " +
                    "it takes argument Pageable"
    )
    @GetMapping("/all")
    public ResponseEntity<?> getAllEvents(Pageable pageable){
        try{
            return ResponseEntity.ok().body(eventService.getAllEvents(pageable));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @Operation(summary = "Get event by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400")
    })
    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEventById(
            @Parameter(name = "eventId", description = "PathVariable")
            @PathVariable Long eventId
    ){
        try {
            return ResponseEntity.ok().body(eventService.getEventById(eventId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Add new Event")
    @Parameters({
            @Parameter(name = "eventPayload", description = "RequestBody")
    })
    @PostMapping("/new")
    public ResponseEntity<?> addNewEvent(@RequestBody EventPayload eventPayload){
        try {
            eventService.addEvent(eventPayload);
            return ResponseEntity.ok().body("Event added successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Delete event based on provided ID")
    @DeleteMapping("/{eventId}")
    public void deleteEvent(
            @Parameter(name = "eventId", description = "PathVariable")
            @PathVariable Long eventId
    ){
             eventService.deleteEvent(eventId);
    }

    @Operation(summary = "Add coach to event")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400"),
    })
    @Parameters({
            @Parameter(name = "event-id", description = "RequestParam"),
            @Parameter(name = "coach-id", description = "RequestParam")
    })
    @PatchMapping("/add-coach")
    public ResponseEntity<?> addCoachToEvent(@RequestParam(name = "event-id") Long eventId, @RequestParam(name = "coach-id") Long coachId){
        try {
            eventService.addCoach(eventId, coachId);
            return ResponseEntity.ok().body("Coach added successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Remove coach from event")
    @Parameters({
            @Parameter(name = "event-id", description = "RequestParam"),
            @Parameter(name = "coach-id", description = "RequestParam")
    })
    @PatchMapping("/remove-coach")
    public ResponseEntity<?> removeCoachFromEvent(@RequestParam(name = "event-id") Long eventId, @RequestParam(name = "coach-id") Long coachId){
        try {
            eventService.removeCoach(eventId, coachId);
            return ResponseEntity.ok().body("Coach removed successfully");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "update Event Data")
    @Parameter(name = "payload", description = "Provide any number of fields from EventDTO model which do you want" +
            " to update")
    @PatchMapping
    public ResponseEntity<?> updateEventData(@RequestBody EventDTO payload){
        try{
            eventService.updateEventData(payload);
            return ResponseEntity.ok().body("Event data updated successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }


}
