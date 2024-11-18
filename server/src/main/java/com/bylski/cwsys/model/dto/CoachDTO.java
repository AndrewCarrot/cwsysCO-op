package com.bylski.cwsys.model.dto;

import java.time.LocalDateTime;

public record CoachDTO(
        Long id,
        String firstName,
        String lastName,
        String pseudonym,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate
) {

}
