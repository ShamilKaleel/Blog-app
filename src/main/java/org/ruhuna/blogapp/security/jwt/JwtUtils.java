package org.ruhuna.blogapp.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.ruhuna.blogapp.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationMs}")
    private Integer jwtExpirationMs;

    @Value("${spring.app.jwtCookieName}")
    private String jwtCookie;

    // Added to allow environment-specific configuration
    @Value("${spring.app.cookieSecure}")
    private boolean cookieSecure;

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) {
            log.debug("JWT cookie found: {}", cookie.getName());
            return cookie.getValue();
        } else {
            log.debug("No JWT cookie found with name: {}", jwtCookie);
        }
        return null;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateTokenFromUsername(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key(), SignatureAlgorithm.HS512)
                .compact();

        log.debug("Generated JWT token for user: {} (expires: {})", username, expiryDate);
        return token;
    }

    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername());

        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt)
                .path("/")                   // Changed from "/api" to "/" to be available across the entire domain
                .maxAge(24 * 60 * 60)        // 24 hours in seconds
                .httpOnly(true)              // Prevents JavaScript access
                .secure(cookieSecure)        // Configurable based on environment
                .sameSite("Lax")             // Changed from "Strict" to "Lax" for better compatibility
                .build();

        log.info("Generated JWT cookie for user: {}", userPrincipal.getUsername());
        return cookie;
    }

    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, "")
                .path("/")                   // Match the path in generateJwtCookie
                .maxAge(0)                   // Expire immediately
                .httpOnly(true)
                .secure(cookieSecure)        // Configurable based on environment
                .sameSite("Lax")             // Match the setting in generateJwtCookie
                .build();

        log.debug("Generated clean JWT cookie for logout");
        return cookie;
    }

    public String getUserNameFromJwtToken(String token) {
        try {
            String username = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            log.debug("Extracted username from JWT: {}", username);
            return username;
        } catch (Exception e) {
            log.error("Could not extract username from token: {}", e.getMessage());
            return null;
        }
    }

    public boolean validateJwtToken(String authToken) {
        if (authToken == null) {
            log.error("JWT token is null");
            return false;
        }

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(authToken);

            log.debug("JWT token validated successfully");
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token format: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        } catch (JwtException e) {
            log.error("JWT parsing or validation error: {}", e.getMessage());
        }
        return false;
    }
}