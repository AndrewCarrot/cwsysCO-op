package com.bylski.cwsys.model.payload;

import com.bylski.cwsys.model.enums.EventType;

import java.time.LocalDateTime;

public record EventPayload(
        int numberOfParticipants,
        int numberOfCoaches,
        int durationInMinutes,
        LocalDateTime dateTime,
        EventType eventType,
        String name
) {
}
