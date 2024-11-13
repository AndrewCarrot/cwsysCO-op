package com.bylski.cwsys.controller;

import com.bylski.cwsys.model.Pass;
import com.bylski.cwsys.model.dto.ClimberDTO;
import com.bylski.cwsys.model.payload.ClimberPayload;
import com.bylski.cwsys.service.inf.ClimberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/climber")
public class ClimberController {
    private final ClimberService climberService;

    public ClimberController(ClimberService climberService) {
        this.climberService = climberService;
    }

    @GetMapping("/all")
    public Page<ClimberDTO> getAllClimbers(Pageable pageable){
        return climberService.getAllClimbers(pageable);
    }

    @GetMapping("/{cardNumber}")
    public ClimberDTO getClimberByCardNumber(@PathVariable String cardNumber){
        return climberService.getClimberByCardNumber(cardNumber);
    }


    @PostMapping("/new")
    public void addNewClimber(@RequestBody ClimberPayload payload){
        climberService.addNewClimber(payload);
    }

    @PostMapping("/{id}/pass")
    public void addNewPass(@PathVariable Long climberId, @RequestBody Pass pass){
        climberService.addNewPass(climberId,pass);
    }

    @DeleteMapping("/{climberId}")
    public void deleteClimber(@PathVariable Long climberId){
        climberService.deleteClimber(climberId);
    }
}
