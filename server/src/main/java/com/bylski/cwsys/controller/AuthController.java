package com.bylski.cwsys.controller;

import com.bylski.cwsys.model.User;
import com.bylski.cwsys.model.dto.LoginRequest;
import com.bylski.cwsys.model.dto.LoginResponse;
import com.bylski.cwsys.service.impl.UserDetailsServiceImpl;
import com.bylski.cwsys.utilz.JwtHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@Tag(name = "AuthController", description = "for now just admin authentication")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
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

    @Operation(summary = "validates jwt token")
    @Parameter(name = "token", description = "Request body of \"token\":\"tokenValue\" ")
    @PostMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestBody Map<String,String> token){

        String username;
        ResponseEntity<String> errorResponse
                = new ResponseEntity<>("Oi, where did you get that token from?" , HttpStatus.FORBIDDEN );
        try{
            username = JwtHelper.extractUsername(token.get("token"));
        }catch (Exception e){
            return errorResponse;
        }

        User userDetails = (User) userDetailsService.loadUserByUsername(username);
        if(JwtHelper.validateToken(token.get("token"),userDetails))
            return new ResponseEntity<>("Nice token bro", HttpStatus.OK);
        return errorResponse;
    }
}
