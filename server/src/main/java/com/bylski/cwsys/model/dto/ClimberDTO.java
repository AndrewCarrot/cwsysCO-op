package com.bylski.cwsys.model.dto;

import com.bylski.cwsys.model.DateAudit;
import com.bylski.cwsys.model.ClimbingGroup;
import com.bylski.cwsys.model.Pass;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public record ClimberDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        LocalDate dateOfBirth,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String note,
        String cardNumber,
        boolean multisport,
        Set<Pass> passes,
        Set<ClimbingGroup> groups
) {
}
