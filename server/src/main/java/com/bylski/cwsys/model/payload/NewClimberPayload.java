package com.bylski.cwsys.model.payload;

import java.time.LocalDate;

public record NewClimberPayload(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        LocalDate dateOfBirth
) {}
