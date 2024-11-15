package com.bylski.cwsys.controller;

import com.bylski.cwsys.model.Coach;
import com.bylski.cwsys.model.Event;
import com.bylski.cwsys.model.dto.CoachDTO;
import com.bylski.cwsys.model.payload.CoachPayload;
import com.bylski.cwsys.service.inf.CoachService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.annotations.NotFound;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Set;

@Tag(name = "Coach Controller", description = "Methods for Coach API")
@RestController
@RequestMapping("/coach")
public class CoachController {
    private final CoachService coachService;

    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @Operation(summary = "Returns all coaches in a List")
    @GetMapping("/all")
    public List<CoachDTO> getAllCoaches(){
        return coachService.getCoaches();
    }

    @Operation(summary = "Get coach with given ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Coach with given ID does not exist in database",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = NotFound.class))
                    })
    })
    @GetMapping("/{coachId}")
    public CoachDTO getCoachById(
            @Parameter(name = "coachId", description = "PathVariable")
            @PathVariable Long coachId
    ){
        return coachService.getCoachById(coachId);
    }

    @Operation(summary = "Returns List of coaches based on provided name")
    @GetMapping("/first-name/{firstName}")
    public List<CoachDTO> getCoachByFirstName(
            @Parameter(name = "firstName", description = "PathVariable")
            @PathVariable String firstName
    ){
        return coachService.getCoachByFirstName(firstName);
    }

    @Operation(summary = "Returns List of coaches based on provided last name")
    @GetMapping("/last-name/{lastName}")
    public List<CoachDTO> getCoachByLastName(
            @Parameter(name = "lastName", description = "PathVariable")
            @PathVariable String lastName
    ){
        return coachService.getCoachByLastName(lastName);
    }

    @Operation(summary = "Get events for given coach")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Coach with given ID does not exist in database",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = NotFound.class))
                    })
    })
    @Parameter(name = "coachId",description = "PathVariable")
    @GetMapping("/event/{coachId}")
    public Set<Event> getEventsForGivenCoach(@PathVariable Long coachId){
        return coachService.getEventsForGivenCoach(coachId);
    }

    @Operation(summary = "Add new Coach")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "409", description = "Coach with given personal number exists in database",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = HttpClientErrorException.Conflict.class))
                    })
    })
    @Parameter(name = "coachPayload", description = "RequestBody")
    @PostMapping("/new")
    public void addNewCoach(@RequestBody CoachPayload coachPayload) throws Exception {
        coachService.addCoach(coachPayload);
    }

    @Operation(summary = "Delete coach based on provided ID")
    @DeleteMapping("/{coachId}")
    public void deleteCoachById(
            @Parameter(name = "coachId", description = "PathVariable")
            @PathVariable Long coachId
    ){
        coachService.deleteCoach(coachId);
    }


}
