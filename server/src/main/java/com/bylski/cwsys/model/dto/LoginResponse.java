package com.bylski.cwsys.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        @Schema(description = "email")
        String usernameOrEmail,
        @Schema(description = "JWT token")
        String token) {

}