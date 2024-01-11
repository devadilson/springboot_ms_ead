package com.ead.authuser.configs.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;


@Log4j2
@Component
public class JwtProvider {

    @Value(value = "${ead.auth.jwtSecret}")
    private String jwtSecret;

    @Value(value = "${ead.auth.jwtExpirationMs}")
    private Long jwtExpirationMs;

     public String generateJwt(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        final String roles = userPrincipal.getAuthorities().stream()
                .map(role -> {
                    return role.getAuthority();
                }).collect(Collectors.joining(","));

         return Jwts.builder()
                 .subject((userPrincipal.getUserId().toString()))
                 .claim("roles", roles)
                 .issuedAt(new Date())
                 .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                 .signWith(getSigningKey(), Jwts.SIG.HS512)
                 .compact();
     }

    public String getSubjectJwt(String token) { return extractClaim(token, Claims::getSubject); }

   public boolean validateJwt(String authToken) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(authToken);
            return true;
        } catch (SecurityException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException  e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException  e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException  e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}
