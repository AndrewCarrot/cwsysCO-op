package com.bylski.cwsys.controller;

import com.bylski.cwsys.model.dto.CoachDTO;
import com.bylski.cwsys.model.payload.CoachPayload;
import com.bylski.cwsys.service.inf.CoachService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Tag(name = "Coach Controller", description = "Methods for Coach API")
@RestController
@RequestMapping("/coach")
public class CoachController {
    private final CoachService coachService;

    private static final Logger logger = Logger.getLogger(CoachController.class.getName());

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
            @Parameter(name = "coachId", description = "Przy indeksach 1-9 czasami nie wywołuje się kontroler," +
                    " chuj wie dlaczego, jeśli poprzedzisz numer zerem - 01,02 wtedy działa zawsze (?) " +
                    "więc pewnie tak byłoby bezpieczniej ")
            @PathVariable Long coachId
    ){
        try{
            logger.log(Level.INFO, "CoachID: " + coachId);
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

    @Operation(summary = "Returns Page of events for given coach that will take place in the future")
    @GetMapping("/active-events/{coachId}")
    public ResponseEntity<?> getActiveEvents(@PathVariable Long coachId, Pageable pageable){
        try{
            return ResponseEntity.ok().body(coachService.getActiveEvents(coachId, pageable));
        }catch (Exception e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @Operation(summary = "Returns Page of events for given coach that took place in the past")
    @Parameter(name = "date", description = "RequestParam, how far back you want to check : \"?date=2022-10-22\" ")
    @GetMapping("/past-events/{coachId}")
    public ResponseEntity<?> getPastEvents(
            @PathVariable Long coachId,
            @RequestParam LocalDate date,
            Pageable pageable
            ){
            try{
                return ResponseEntity.ok().body(coachService.getPastEvents(coachId,date,pageable));
            }catch (Exception e){
                return ResponseEntity.status(404).body(e.getMessage());
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
