package com.patryk.mech.manageitup.security;

import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

@Service
public class JwtService {

//    private final SecretKey key;
//    private final long expirationMinutes;
//
//    public JwtService(
//            @Value("${app.jwt.secret}") String secret,
//            @Value("${app.jwt.expiration-minutes}") long expirationMinutes) {
//        // If your secret is base64, use: Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
//        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
//        this.expirationMinutes = expirationMinutes;
//    }
//
//    public String generateToken(UserDetails user) {
//        Instant now = Instant.now();
//        Map<String, Object> claims = new HashMap<>();
//        // include roles so the front end can gate routes
//        claims.put("roles", user.getAuthorities().stream().map(a -> a.getAuthority()).toList());
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(user.getUsername())     // email/username
//                .setIssuedAt(Date.from(now))
//                .setExpiration(Date.from(now.plusSeconds(expirationMinutes * 60)))
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public String extractUsername(String token) {
//        return parseAllClaims(token).getSubject();
//    }
//
//    public List<String> extractRoles(String token) {
//        Object r = parseAllClaims(token).get("roles");
//        if (r instanceof List<?> list) {
//            return list.stream().map(Object::toString).toList();
//        }
//        return List.of();
//    }
//
//    public boolean isTokenValid(String token, UserDetails user) {
//        try {
//            Claims c = parseAllClaims(token);
//            boolean subjectMatches = user.getUsername().equals(c.getSubject());
//            Date exp = c.getExpiration();
//            return subjectMatches && (exp == null || exp.after(new Date()));
//        } catch (JwtException | IllegalArgumentException e) {
//            return false;
//        }
//    }
//
//    private Claims parseAllClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
}
