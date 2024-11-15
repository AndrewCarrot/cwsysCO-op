package com.bylski.cwsys.controller;

import com.bylski.cwsys.model.Pass;
import com.bylski.cwsys.model.dto.ClimberDTO;
import com.bylski.cwsys.model.payload.ClimberPayload;
import com.bylski.cwsys.service.inf.ClimberService;
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
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Climber Controller", description = "Methods for Climber API")
@RestController
@RequestMapping("/climber")
public class ClimberController {
    private final ClimberService climberService;

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
            @ApiResponse(responseCode = "404", description = "Given card number is not in the system",
                    content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotFound.class))
            })
    })
    @GetMapping("/{cardNumber}")
    public ClimberDTO getClimberByCardNumber(
            @Parameter(
                    description = "climbers assigned physical card number"
            )
            @PathVariable String cardNumber
    ){
        return climberService.getClimberByCardNumber(cardNumber);
    }


    @Operation(
            summary = "Add new climber",
            description = "It takes ClimberPayload and creates a new Climber if email does not exist in a database"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "409", description = "Climber with given email already exists")
    })
    @PostMapping("/new")
    @Parameters({@Parameter(name = "payload", description = "Request Body of a ClimberPayload")})
    public void addNewClimber(
            @RequestBody ClimberPayload payload
    ){
        climberService.addNewClimber(payload);
    }

    @Operation(
            summary = "Add new pass to existing climber"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Climber with given id does not exist"),
            @ApiResponse(
                    responseCode = "409",
                    description = "Climber already has pass with given type, you can't have two passes with the same type"
            )
    })
    @Parameters({
           @Parameter(name = "pass", description = "request body of a Pass entity",required = true)
    })
    @PostMapping("/{id}/pass")
    public void addNewPass(
            @Parameter(name = "id", description = "ID of a climber", required = true)
            @PathVariable Long climberId,
            @RequestBody Pass pass
    ){
        climberService.addNewPass(climberId,pass);
    }

    @Operation(
            summary = "Delete existing climber "
    )
    @ApiResponse
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
}
