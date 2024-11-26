package com.bylski.cwsys.utilz;

import com.bylski.cwsys.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
public class JwtHelper {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final int MINUTES = 60;

    private static final Logger logger = Logger.getLogger(JwtHelper.class.getName());

    public static String generateToken(String usernameOrEmail) {
        var now = Instant.now();
        return Jwts.builder()
                .setSubject(usernameOrEmail)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(MINUTES, ChronoUnit.MINUTES)))
                .signWith(SECRET_KEY,SignatureAlgorithm.HS256)
                .compact();
    }

    public static String extractUsername(String token) {
        return getTokenBody(token).getSubject();
    }

    public static Boolean validateToken(String token, User userDetails) {
        final String username = extractUsername(token);
        final boolean isTokenValid = username.equals(userDetails.getUsername()) || username.equals(userDetails.getEmail()) && !isTokenExpired(token);

        logger.log(Level.INFO, "Username: " + username);
        logger.log(Level.INFO, isTokenValid ? "Token: valid" : "Invalid token");

        return isTokenValid;
    }

    private static Claims getTokenBody(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) { // Invalid signature or expired token
            logger.log(Level.WARNING, "Error while extracting token body");
            throw new AccessDeniedException("Access denied: " + e.getMessage());
        }
    }

    private static boolean isTokenExpired(String token) {
        Claims claims = getTokenBody(token);
        return claims.getExpiration().before(new Date());
    }
}