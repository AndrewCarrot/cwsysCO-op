package com.bylski.cwsys.controller;

import com.bylski.cwsys.model.dto.LoginRequest;
import com.bylski.cwsys.model.dto.LoginResponse;
import com.bylski.cwsys.utilz.JwtHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AuthController", description = "for now just admin authentication")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController( AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * The authenticationManager.authenticate() method will internally call
     * loadUserByUsername() method from our CustomUserDetailsService class.
     */
    @Operation(summary = "log into system")
    @Parameter(name = "request", description = "RequestBody")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.usernameOrEmail(), request.password()));
        String token = JwtHelper.generateToken(request.usernameOrEmail());
        return ResponseEntity.ok(new LoginResponse(request.usernameOrEmail(), token));
    }
}
