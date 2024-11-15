package com.bylski.cwsys.model.payload;

import jakarta.validation.constraints.NotNull;

public record CoachPayload(
        @NotNull
        String firstName,
        @NotNull
        String lastName,
        @NotNull
        String personalNumber
) {
}
