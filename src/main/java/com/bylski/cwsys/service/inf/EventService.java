package com.bylski.cwsys.service.inf;

import com.bylski.cwsys.model.Event;
import com.bylski.cwsys.model.dto.EventDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService{
    Page<EventDTO> getAllEvents(Pageable pageable);
    EventDTO getEventById(Long eventId);
    void addEvent(Event event);
    void deleteEvent(Long eventId);
    void addCoach(Long eventId, Long coachId);
    void removeCoach(Long eventId, Long coachId);
}
