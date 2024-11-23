package com.bylski.cwsys.controller;

import com.bylski.cwsys.model.Pass;
import com.bylski.cwsys.model.dto.ClimberDTO;
import com.bylski.cwsys.model.payload.NewClimberPayload;
import com.bylski.cwsys.service.inf.ClimberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Climber Controller", description = "Methods for Climber API")
@RestController
@RequestMapping("/climber")
public class ClimberController {
    private final ClimberService climberService;
    private long lastRequest = System.currentTimeMillis();

    public ClimberController(ClimberService climberService) {
        this.climberService = climberService;
    }

    @Operation(
            summary = "Get All Climbers",
            description = "Returns Page object containing all Climbers, it takes argument Pageable where you can " +
                    "specify how many pages you want to get, how big the page should be etc."
    )
    @GetMapping("/all")
    public Page<ClimberDTO> getAllClimbers(Pageable pageable){
        return climberService.getAllClimbers(pageable);
    }

    @Operation(
            summary = "Get climber by card number"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400")
    })
    @GetMapping("/{cardNumber}")
    public ResponseEntity<?> getClimberByCardNumber(
            @Parameter(
                    description = "climbers assigned physical card number"
            )
            @PathVariable String cardNumber
    ){
        try {
            ClimberDTO climberDTO = climberService.getClimberByCardNumber(cardNumber);
            return new ResponseEntity<>(climberDTO, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @Operation(
            summary = "Add new climber",
            description = "It takes ClimberPayload and creates a new Climber if email does not exist in a database"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400")
    })
    @PostMapping("/new")
    @Parameters({@Parameter(name = "payload", description = "Request Body of a ClimberPayload")})
    public ResponseEntity<?> addNewClimber(
            @RequestBody NewClimberPayload payload
    ){
        long now = System.currentTimeMillis();
        if(now-lastRequest < 10000) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You have to wait at least" +
                    " 10 seconds before next request");
        }
        try {
            climberService.addNewClimber(payload);
            lastRequest = now;
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok("Successfully added new climber");
    }

    @Operation(
            summary = "Add new pass to existing climber"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400")
    })
    @Parameters({
           @Parameter(name = "pass", description = "request body of a Pass entity",required = true)
    })
    @PostMapping("/{climberId}/pass")
    public ResponseEntity<String> addNewPass(
            @Parameter(name = "climberId", description = "ID of a climber", required = true)
            @PathVariable Long climberId,
            @RequestBody Pass pass
    ){
        try{
            climberService.addNewPass(climberId,pass);
            return ResponseEntity.ok("Pass successfully added to climber");
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Delete existing climber "
    )
    @DeleteMapping("/{climberId}")
    public void deleteClimber(
            @Parameter(
                    description = "ID of the climber to be deleted",
                    required = true
            )
            @PathVariable Long climberId
    ){
        climberService.deleteClimber(climberId);
    }

    @Operation(summary = "Update climber data")
    @Parameter(name = "climber", description = "RequestBody contains id of a climber which we want to update" +
            " + new values for the climber's fields. You can pass any number of fields," +
            " eg. if you want to update two specific fields, you don't have to pass entire climber object," +
            " just these two fields")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400")
    })
    @PatchMapping
    public ResponseEntity<?> updateClimberData(@RequestBody ClimberDTO payload){
        try {
            climberService.updateClimberData(payload);
        }catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok().body("Successfully updated climber");
    }
}
