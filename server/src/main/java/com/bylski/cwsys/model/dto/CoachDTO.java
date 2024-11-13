package com.bylski.cwsys.model.dto;

import com.bylski.cwsys.model.Event;

import java.util.Set;

public record CoachDTO(
        Long id,
        String firstName,
        String lastName,
        String personalNumber,
        Set<Event> eventSet
) {

}
