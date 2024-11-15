package com.bylski.cwsys.model.dto;

import com.bylski.cwsys.model.Coach;
import com.bylski.cwsys.model.enums.EventType;

import java.time.LocalDateTime;
import java.util.Set;

public record EventDTO(
        int numberOfParticipants,
        int numberOfCoaches,
        int durationInMinutes,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        LocalDateTime dateTime,
        EventType eventType,
        String name,
        Set<Coach> coachSet
) {}
