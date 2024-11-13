package com.bylski.cwsys.model.dto;

import com.bylski.cwsys.model.Event;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EventDTOMapper implements Function<Event, EventDTO> {

    @Override
    public EventDTO apply(Event event) {
        return new EventDTO(
                event.getNumberOfParticipants(),
                event.getNumberOfCoaches(),
                event.getDurationInMinutes(),
                event.getDateTime(),
                event.getEventType(),
                event.getName(),
                event.getCoachSet()
        );
    }
}
