package com.bylski.cwsys.service.impl;

import com.bylski.cwsys.exception.ResourceAlreadyExistsException;
import com.bylski.cwsys.exception.ResourceNotFoundException;
import com.bylski.cwsys.model.Coach;
import com.bylski.cwsys.model.dto.ClimbingGroupDTO;
import com.bylski.cwsys.model.dto.CoachDTO;
import com.bylski.cwsys.model.dto.EventDTO;
import com.bylski.cwsys.model.payload.CoachPayload;
import com.bylski.cwsys.repository.CoachRepository;
import com.bylski.cwsys.service.inf.CoachService;
import com.bylski.cwsys.service.inf.EventService;
import com.bylski.cwsys.utilz.Patcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;
    private final EventService eventService;
    private final ObjectMapper objectMapper;

    public CoachServiceImpl(
            CoachRepository coachRepository, EventService eventService,
            ObjectMapper objectMapper
    ) {
        this.coachRepository = coachRepository;
        this.eventService = eventService;
        this.objectMapper = objectMapper;
    }


    @Override
    public void addCoach(CoachPayload coachPayload){
        Optional<Coach> result = coachRepository.findByPseudonym(coachPayload.pseudonym());

        if(result.isPresent())
            throw new ResourceAlreadyExistsException("Coach","pseudonym",coachPayload.pseudonym());

        Coach coach = new Coach(
                coachPayload.firstName(),
                coachPayload.lastName(),
                coachPayload.pseudonym()
        );

        coachRepository.save(coach);
    }

    @Override
    public void deleteCoach(Long coachId) {
        coachRepository.deleteById(coachId);
    }

    @Override
    public List<CoachDTO> getCoaches() {
        return coachRepository.findAll().stream().map(o->objectMapper.convertValue(o,CoachDTO.class)).toList();

    }

    //TODO
    @Override
    public Page<EventDTO> getActiveEvents(Long coachId, Pageable pageable) {
        List<EventDTO> events = eventService.getActiveEvents();
        List<EventDTO> result = new ArrayList<>();

        for(EventDTO e: events){
            for(Coach c: e.coachSet()){
                if(c.getId().equals(coachId)){
                    result.add(e);
                    break;
                }
            }
        }

        //------------------------------------------------
        if (pageable.isUnpaged())
            pageable = PageRequest.of(0,10);
        return new PageImpl<>(result, pageable, events.size());
    }

    //TODO
    @Override
    public Page<EventDTO> getPastEvents(Long coachId, LocalDate from, Pageable pageable) {
        List<EventDTO> events = eventService.getPastEvents(from);
        List<EventDTO> result = new ArrayList<>();

        for(EventDTO e: events){
            for(Coach c: e.coachSet()){
                if(c.getId().equals(coachId)){
                    result.add(e);
                    break;
                }
            }
        }

        //---------------------------------------------------------
        if (pageable.isUnpaged())
            pageable = PageRequest.of(0,10);
        return new PageImpl<>(result, pageable, events.size());
    }

    @Override
    public CoachDTO getCoachById(Long coachId) {
        Coach coach = coachRepository.findById(coachId)
                .orElseThrow(()->new ResourceNotFoundException("Coach","id",coachId));
        return objectMapper.convertValue(coach,CoachDTO.class);

    }

    @Override
    public List<CoachDTO> getCoachByFirstName(String firstName) {
        return coachRepository
                .findAllByFirstName(firstName)
                .stream()
                .map(o->objectMapper.convertValue(o,CoachDTO.class))
                .toList();
    }

    @Override
    public List<CoachDTO> getCoachByLastName(String lastName) {
        return coachRepository
                .findAllByLastName(lastName)
                .stream()
                .map(o->objectMapper.convertValue(o,CoachDTO.class))
                .toList();
    }

    @Override
    public List<ClimbingGroupDTO> getClimbingGroups(Long coachId){
        Coach result = coachRepository.findById(coachId)
                .orElseThrow(()->new ResourceNotFoundException("Coach","id",coachId));

        return result
                .getClimbingGroupSet()
                .stream()
                .map(o->objectMapper.convertValue(o,ClimbingGroupDTO.class))
                .toList();
    }

    @Override
    public void updateCoachData(CoachDTO payload) {
        Coach existing = coachRepository.findById(payload.id())
                .orElseThrow(()->new ResourceNotFoundException("Coach","id",payload.id()));
        if (payload.pseudonym() != null && payload.pseudonym().equals(existing.getPseudonym()))
                throw new ResourceAlreadyExistsException("Coach","pseudonym",payload.pseudonym());

        Coach incomplete = objectMapper.convertValue(payload,Coach.class);

        try{
            Patcher.objectPatcher(existing,incomplete);
            coachRepository.save(existing);
        }catch(IllegalAccessException e){
            e.getCause();
        }
    }
}
