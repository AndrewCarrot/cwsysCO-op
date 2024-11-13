package com.bylski.cwsys.model.payload;

import java.time.LocalDate;

public record ClimberPayload(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        LocalDate dateOfBirth
) {}
