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
import org.springframework.web.bind.annotation.*;

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
    public Page<EventDTO> getAllEvents(Pageable pageable){
        return eventService.getAllEvents(pageable);
    }

    @Operation(summary = "Get event by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Event with given ID does not exist in database",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = NotFound.class))
                    })
    })
    @GetMapping("/{eventId}")
    public EventDTO getEventById(
            @Parameter(name = "eventId", description = "PathVariable")
            @PathVariable Long eventId
    ){
        return eventService.getEventById(eventId);
    }

    @Operation(summary = "Add new Event")
    @Parameters({
            @Parameter(name = "eventPayload", description = "RequestBody")
    })
    @PostMapping("/new")
    public void addNewEvent(@RequestBody EventPayload eventPayload){
        eventService.addEvent(eventPayload);
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
            @ApiResponse(responseCode = "404", description = "Event or Coach does not exists in database"),
            @ApiResponse(responseCode = "409", description = "Given coach is already assigned to this event")
    })
    @Parameters({
            @Parameter(name = "event-id", description = "RequestParam"),
            @Parameter(name = "coach-id", description = "RequestParam")
    })
    @PatchMapping("/add-coach")
    public void addCoachToEvent(@RequestParam(name = "event-id") Long eventId, @RequestParam(name = "coach-id") Long coachId){
        eventService.addCoach(eventId, coachId);
    }

    @Operation(summary = "Remove coach from event")
    @Parameters({
            @Parameter(name = "event-id", description = "RequestParam"),
            @Parameter(name = "coach-id", description = "RequestParam")
    })
    @PatchMapping("/remove-coach")
    public void removeCoachFromEvent(@RequestParam(name = "event-id") Long eventId, @RequestParam(name = "coach-id") Long coachId){
        eventService.removeCoach(eventId, coachId);
    }


}
