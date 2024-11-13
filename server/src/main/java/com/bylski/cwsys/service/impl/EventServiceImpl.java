package com.bylski.cwsys.service.impl;

import com.bylski.cwsys.exception.ResourceNotFoundException;
import com.bylski.cwsys.model.Coach;
import com.bylski.cwsys.model.Event;
import com.bylski.cwsys.model.dto.EventDTO;
import com.bylski.cwsys.model.dto.EventDTOMapper;
import com.bylski.cwsys.repository.CoachRepository;
import com.bylski.cwsys.repository.EventRepository;
import com.bylski.cwsys.service.inf.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CoachRepository coachRepository;
    private final EventDTOMapper mapper;

    public EventServiceImpl(EventRepository eventRepository, CoachRepository coachRepository, EventDTOMapper mapper) {
        this.eventRepository = eventRepository;
        this.coachRepository = coachRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<EventDTO> getAllEvents(Pageable pageable) {
        return new PageImpl<>(eventRepository.findAll(pageable).stream().map(mapper).toList());
    }

    @Override
    public EventDTO getEventById(Long eventId) {
        Optional<EventDTO> event = eventRepository.findById(eventId).map(mapper);
        if(event.isEmpty())
            throw new ResourceNotFoundException("Event","id",eventId);
        return event.get();
    }

    @Override
    public void addEvent(Event event){
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    @Override
    public void addCoach(Long eventId, Long coachId) {
        Coach coach = coachRepository.findById(coachId)
                .orElseThrow(()->new RuntimeException("Coach with given ID doesn't exist"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(()->new RuntimeException("Event with given ID doesn't exist"));

        coach.getEventSet().add(event);
        event.getCoachSet().add(coach);
        eventRepository.save(event);

    }

    @Override
    public void removeCoach(Long eventId, Long coachId) {
        Coach coach = coachRepository.findById(coachId)
                .orElseThrow(()->new RuntimeException("Coach with given ID doesn't exist"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(()->new RuntimeException("Event with given ID doesn't exist"));

        event.getCoachSet().remove(coach);
        coach.getEventSet().remove(event);
        coachRepository.save(coach);
    }
}
