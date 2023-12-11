package com.ead.authuser.configs.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

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
        UserDetails userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
         return Jwts.builder()
                 .subject((userPrincipal.getUsername()))
                 .issuedAt(new Date())
                 .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                 .signWith(getSigningKey(), Jwts.SIG.HS512)
                 .compact();
     }

    public String getUsernameJwt(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateJwt(String authToken) {
        return !isTokenExpired(authToken);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
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
