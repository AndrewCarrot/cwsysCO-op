package com.bylski.cwsys.model.dto;

import com.bylski.cwsys.model.Event;

import java.time.LocalDateTime;
import java.util.Set;

public record CoachDTO(
        Long id,
        String firstName,
        String lastName,
        String personalNumber,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate
) {

}
