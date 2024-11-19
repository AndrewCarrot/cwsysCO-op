package com.bylski.cwsys.service.impl;

import com.bylski.cwsys.exception.ResourceAlreadyExistsException;
import com.bylski.cwsys.exception.ResourceNotFoundException;
import com.bylski.cwsys.model.Coach;
import com.bylski.cwsys.model.Event;
import com.bylski.cwsys.model.dto.ClimbingGroupDTO;
import com.bylski.cwsys.model.dto.CoachDTO;
import com.bylski.cwsys.model.dto.CoachDTOMapper;
import com.bylski.cwsys.model.payload.CoachPayload;
import com.bylski.cwsys.repository.CoachRepository;
import com.bylski.cwsys.service.inf.CoachService;
import com.bylski.cwsys.utilz.Patcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;
    private final CoachDTOMapper coachDTOMapper;
    private final ObjectMapper objectMapper;

    public CoachServiceImpl(
            CoachRepository coachRepository,
            CoachDTOMapper mapper,
            ObjectMapper objectMapper
    ) {
        this.coachRepository = coachRepository;
        this.coachDTOMapper = mapper;
        this.objectMapper = objectMapper;
    }


    @Override
    public void addCoach(CoachPayload coachPayload){
        Optional<Coach> result = coachRepository.findByPseudonym(coachPayload.personalNumber());

        if(result.isPresent())
            throw new ResourceAlreadyExistsException("Coach","personal number",coachPayload.personalNumber());

        Coach coach = new Coach(
                coachPayload.firstName(),
                coachPayload.lastName(),
                coachPayload.personalNumber()
        );

        coachRepository.save(coach);
    }

    @Override
    public void deleteCoach(Long coachId) {
        coachRepository.deleteById(coachId);
    }

    @Override
    public List<CoachDTO> getCoaches() {
        return coachRepository.findAll().stream().map(coachDTOMapper).toList();
    }

    //TODO jeśli nie pozbywamy się starych eventów z bazy ten set może zrobić się dosyć spory,
    @Override
    public Set<Event> getEventsForGivenCoach(Long coachId) {
        Coach result = coachRepository.findById(coachId)
                .orElseThrow(()->new ResourceNotFoundException("Coach","id",coachId));
        return result.getEventSet();
    }

    @Override
    public CoachDTO getCoachById(Long coachId) {
        Optional<CoachDTO> coach = coachRepository.findById(coachId).map(this.coachDTOMapper);
        if (coach.isEmpty())
            throw new ResourceNotFoundException("Coach","id",coachId);
        return coach.get();
    }

    @Override
    public List<CoachDTO> getCoachByFirstName(String firstName) {
        return coachRepository.findAllByFirstName(firstName).stream().map(coachDTOMapper).toList();
    }

    @Override
    public List<CoachDTO> getCoachByLastName(String lastName) {
        return coachRepository.findAllByLastName(lastName).stream().map(coachDTOMapper).toList();
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
        }catch (Exception e){
            e.getCause();
        }
    }
}
