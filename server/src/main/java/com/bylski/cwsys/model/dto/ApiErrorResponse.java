package com.bylski.cwsys.model.dto;

public record ApiErrorResponse(
        //@Schema(description = "Error code")
        int errorCode,
        // @Schema(description = "Error description")
        String description
) {
}
