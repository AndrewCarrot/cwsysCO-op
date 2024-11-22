package com.bylski.cwsys.controller;

import com.bylski.cwsys.model.Coach;
import com.bylski.cwsys.model.Event;
import com.bylski.cwsys.model.dto.ClimbingGroupDTO;
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
import org.apache.coyote.Response;
import org.hibernate.annotations.NotFound;
import org.springframework.http.ResponseEntity;
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
            @ApiResponse(responseCode = "400")
    })
    @GetMapping("/{coachId}")
    public ResponseEntity<?> getCoachById(
            @Parameter(name = "coachId", description = "PathVariable")
            @PathVariable Long coachId
    ){
        try{
            return ResponseEntity.ok().body(coachService.getCoachById(coachId));
        }catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
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
            @ApiResponse(responseCode = "400")
    })
    @Parameter(name = "coachId",description = "PathVariable")
    @GetMapping("/event/{coachId}")
    public ResponseEntity<?> getEventsForGivenCoach(@PathVariable Long coachId){
        try{
            return ResponseEntity.ok().body(coachService.getEventsForGivenCoach(coachId));
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @Operation(summary = "Add new Coach")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400")
    })
    @Parameter(name = "coachPayload", description = "RequestBody")
    @PostMapping("/new")
    public ResponseEntity<?> addNewCoach(@RequestBody CoachPayload coachPayload){
        try {
            coachService.addCoach(coachPayload);
            return ResponseEntity.ok().body("Coach added successfully");
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @Operation(summary = "Delete coach based on provided ID")
    @DeleteMapping("/{coachId}")
    public void deleteCoachById(
            @Parameter(name = "coachId", description = "PathVariable")
            @PathVariable Long coachId
    ){
        coachService.deleteCoach(coachId);
    }

    @Operation(summary = "Get climbing groups for given coach")
    @Parameter(name = "coachId", description = "PathVariable")
    @GetMapping("/climbing-group/{coachId}")
    public ResponseEntity<?> getClimbingGroups(@PathVariable Long coachId){
        try {
            return ResponseEntity.ok().body(coachService.getClimbingGroups(coachId));
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @Operation(summary = "Update coach data")
    @Parameter(name = "payload", description = "Provide any number of fields from CoachDTO model, just those which you want" +
            " to update")
    @PatchMapping
    public ResponseEntity<?> updateCoachData(@RequestBody CoachDTO payload){
        try {
            coachService.updateCoachData(payload);
            return ResponseEntity.ok().body("Coach data updated successfully");
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
