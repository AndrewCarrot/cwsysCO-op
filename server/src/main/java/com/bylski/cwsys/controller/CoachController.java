package com.bylski.cwsys.controller;

import com.bylski.cwsys.model.Coach;
import com.bylski.cwsys.model.Event;
import com.bylski.cwsys.model.dto.CoachDTO;
import com.bylski.cwsys.service.inf.CoachService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/coach")
public class CoachController {
    private final CoachService coachService;

    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @GetMapping("/all")
    public List<CoachDTO> getAllCoaches(){
        return coachService.getCoaches();
    }

    @GetMapping("/{coachId}")
    public CoachDTO getCoachById(@PathVariable Long coachId){
        return coachService.getCoachById(coachId);
    }

    @GetMapping("/first-name/{firstName}")
    public List<CoachDTO> getCoachByFirstName(@PathVariable String firstName){
        return coachService.getCoachByFirstName(firstName);
    }

    @GetMapping("/last-name/{lastName}")
    public List<CoachDTO> getCoachByLastName(@PathVariable String lastName){
        return coachService.getCoachByLastName(lastName);
    }
    //TODO
    @GetMapping("/event/{coachId}")
    public Set<Event> getEventsForGivenCoach(@PathVariable Long coachId){
        return coachService.getEventsForGivenCoach(coachId);
    }

    @PostMapping("/new")
    public void addNewCoach(@RequestBody Coach coach) throws Exception {
        coachService.addCoach(coach);
    }

    @DeleteMapping("/{coachId}")
    public void deleteCoachById(@PathVariable Long coachId){
        coachService.deleteCoach(coachId);
    }


}
