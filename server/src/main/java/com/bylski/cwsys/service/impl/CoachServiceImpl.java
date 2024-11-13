package com.bylski.cwsys.service.impl;

import com.bylski.cwsys.exception.ResourceAlreadyExistsException;
import com.bylski.cwsys.exception.ResourceNotFoundException;
import com.bylski.cwsys.model.Coach;
import com.bylski.cwsys.model.Event;
import com.bylski.cwsys.model.dto.CoachDTO;
import com.bylski.cwsys.model.dto.CoachDTOMapper;
import com.bylski.cwsys.repository.CoachRepository;
import com.bylski.cwsys.service.inf.CoachService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;
    private final CoachDTOMapper mapper;

    public CoachServiceImpl(CoachRepository coachRepository, CoachDTOMapper mapper) {
        this.coachRepository = coachRepository;
        this.mapper = mapper;
    }


    @Override
    public void addCoach(Coach coach){
        List<Coach> existingCoaches = coachRepository.findAllByLastName(coach.getLastName());
        for(Coach c: existingCoaches){
            if(c.getFirstName().equals(coach.getFirstName()))
                throw new ResourceAlreadyExistsException("Coach", "first name & last name",
                        coach.getFirstName() + " " + coach.getLastName());
        }
        coachRepository.save(coach);
    }

    @Override
    public void deleteCoach(Long coachId) {
        coachRepository.deleteById(coachId);
    }

    @Override
    public List<CoachDTO> getCoaches() {
        return coachRepository.findAll().stream().map(mapper).toList();
    }

    //TODO jeśli nie pozbywamy się starych eventów z bazy ten set może zrobić się dosyć spory
    @Override
    public Set<Event> getEventsForGivenCoach(Long coachId) {
        Coach result = coachRepository.findById(coachId)
                .orElseThrow(()->new ResourceNotFoundException("Coach","id",coachId));
        return result.getEventSet();
    }

    @Override
    public CoachDTO getCoachById(Long coachId) {
        Optional<CoachDTO> coach = coachRepository.findById(coachId).map(mapper);
        if (coach.isEmpty())
            throw new ResourceNotFoundException("Coach","id",coachId);
        return coach.get();
    }

    @Override
    public List<CoachDTO> getCoachByFirstName(String firstName) {
        return coachRepository.findAllByFirstName(firstName).stream().map(mapper).toList();
    }

    @Override
    public List<CoachDTO> getCoachByLastName(String lastName) {
        return coachRepository.findAllByLastName(lastName).stream().map(mapper).toList();
    }

}
