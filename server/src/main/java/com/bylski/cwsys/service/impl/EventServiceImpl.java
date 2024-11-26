package com.bylski.cwsys.service.impl;

import com.bylski.cwsys.exception.ResourceAlreadyExistsException;
import com.bylski.cwsys.exception.ResourceNotFoundException;
import com.bylski.cwsys.model.ClimbingGroup;
import com.bylski.cwsys.model.Coach;
import com.bylski.cwsys.model.Event;
import com.bylski.cwsys.model.dto.EventDTO;
import com.bylski.cwsys.model.payload.EventPayload;
import com.bylski.cwsys.repository.CoachRepository;
import com.bylski.cwsys.repository.EventRepository;
import com.bylski.cwsys.service.inf.EventService;
import com.bylski.cwsys.utilz.Patcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CoachRepository coachRepository;
    private final ObjectMapper objectMapper;

    public EventServiceImpl(
            EventRepository eventRepository,
            CoachRepository coachRepository,
            ObjectMapper objectMapper
    ) {
        this.eventRepository = eventRepository;
        this.coachRepository = coachRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Page<EventDTO> getAllEvents(Pageable pageable) {
        if (pageable.isUnpaged())
                pageable = PageRequest.of(0,10);
        List<EventDTO> eventDTOList = eventRepository
                        .findAll(pageable)
                        .stream()
                        .map(o->objectMapper.convertValue(o,EventDTO.class))
                        .toList();
        return new PageImpl<>(eventDTOList,pageable,eventDTOList.size());
    }

    @Override
    public EventDTO getEventById(Long eventId) {
        Optional<EventDTO> event = eventRepository
                        .findById(eventId)
                        .map(o->objectMapper.convertValue(o,EventDTO.class));
        if(event.isEmpty())
            throw new ResourceNotFoundException("Event","id",eventId);
        return event.get();
    }

    @Override
    public void addEvent(EventPayload eventPayload){
        eventRepository.save(new Event(
                eventPayload.numberOfParticipants(),
                eventPayload.numberOfCoaches(),
                eventPayload.durationInMinutes(),
                eventPayload.dateTime(),
                eventPayload.eventType(),
                eventPayload.name()
        ));
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

        // check if coach is already assigned to this event
        Optional<Coach> optionalCoach = event.getCoachSet()
                .stream()
                .filter(c->c.getId().equals(coachId))
                .findAny();
        if(optionalCoach.isPresent())
            throw new ResourceAlreadyExistsException("Coach","id",coachId);

        // check if coach is free at given time
        //-------------------------------------

        // events
        LocalTime eventTime = event.getDateTime().toLocalTime();
        LocalDate eventDate = event.getDateTime().toLocalDate();
        int eventDuration = event.getDurationInMinutes();

        Optional<Event> optionalEvent = coach.getEventSet()
                .stream()
                .filter(e -> e.getDateTime().toLocalDate().equals(eventDate))
                .filter(e -> {
                   LocalTime time = e.getDateTime().toLocalTime();
                   int duration = e.getDurationInMinutes();
                 return eventTime.isBefore(time) && eventTime.plusMinutes(eventDuration).isAfter(time) ||
                  time.isBefore(eventTime) && time.plusMinutes(duration).isAfter(eventTime);
                })
                .findAny();
        if(optionalEvent.isPresent())
            throw new ResourceAlreadyExistsException("Coach already has event assigned at given time");

        //groups
        Optional<ClimbingGroup> optionalClimbingGroup = coach.getClimbingGroupSet()
                .stream()
                .filter(c -> c.getDayOfWeek().equals(eventDate.getDayOfWeek()))
                .filter( c -> {
                    LocalTime time = c.getClassTime();
                    int duration = c.getDurationInMinutes();
                    return eventTime.isBefore(time) && eventTime.plusMinutes(eventDuration).isAfter(time) ||
                            time.isBefore(eventTime) && time.plusMinutes(duration).isAfter(eventTime);
                })
                .findAny();

        if (optionalClimbingGroup.isPresent())
            throw new ResourceAlreadyExistsException("Coach already has group assigned at given time");

        //-----------------------------------------------


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

    @Override
    public void updateEventData(EventDTO payload) {
        Event existing = eventRepository.findById(payload.id())
                .orElseThrow(()->new ResourceNotFoundException("Event","id",payload.id()));

        Event incomplete = objectMapper.convertValue(payload, Event.class);

        try{
            Patcher.objectPatcher(existing,incomplete);
            eventRepository.save(existing);
        }catch(IllegalAccessException e){
            e.getCause();
        }

    }

    @Override
    public List<EventDTO> getActiveEvents(){
        return eventRepository.getActiveEvents().stream()
                .map(o->objectMapper.convertValue(o,EventDTO.class))
                .toList();
    }

    @Override
    public List<EventDTO> getPastEvents(LocalDate from) {
        return eventRepository.getPastEvents(from).stream()
                        .map(o->objectMapper.convertValue(o,EventDTO.class))
                        .toList();
    }
}
