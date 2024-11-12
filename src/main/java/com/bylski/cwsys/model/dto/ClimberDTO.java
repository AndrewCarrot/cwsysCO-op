package com.bylski.cwsys.model.dto;

import com.bylski.cwsys.model.ClimbingGroup;
import com.bylski.cwsys.model.Pass;

import java.util.Set;

public record ClimberDTO(
        Long id,
        String cardNumber,
        String firstName,
        String lastName,
        String note,
        String phoneNumber,
        boolean multisport,
        Set<Pass> passes,
        Set<ClimbingGroup> groups
) {
}
