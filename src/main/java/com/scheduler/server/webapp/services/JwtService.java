package com.scheduler.server.webapp.services;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.scheduler.server.webapp.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${expiry.time}")
    private int expiry;

    @Value("${jwt.secret-private-key.path}")
    private String privateKeyPath;

    @Value("${jwt.secret-public-key.path}")
    private String publicKeyPath;

    public String generateJwtToken(User user) {
        Map<String, Object> map = addClaims(user);
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS256");
        headers.put("typ", "JWT");
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(3, ChronoUnit.DAYS);

        String token = Jwts.builder().issuer("GokulaKrishnanE")
                .setSubject(user.getEmail())
                .claims(map)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiration))
                .signWith(Keys.hmacShaKeyFor(readKeyFile(privateKeyPath).getBytes()))
                .compact();
        return token;
    }

    private Map<String, Object> addClaims(User user) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("email", user.getEmail());
        userMap.put("roles", user.getAuthorities());
        return userMap;
    }

    public String extractUserName(String token) {
        return "";
    }

    // private <T> T extractClaim(String token, Function<Claims, T> claimFunction) {
    // final Claims claims = null;
    // return;
    // }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(readKeyFile(publicKeyPath)).build().parseClaimsJws(token).getPayload();
    }

    private String readKeyFile(String path) {
        String keyFile;
        try {
            keyFile = Files.readString(Paths.get(path), StandardCharsets.UTF_8);
            return keyFile;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private boolean checkExpiration(Date expiration) {
        return expiration != null && expiration.before(new Date());
    }

    public boolean verifyJwtToken(String token, User user) {
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(readKeyFile(publicKeyPath).getBytes()))
                .build()
                .parseSignedClaims(token).getPayload();
        Date expiration = claims.getExpiration();
        if (!checkExpiration(expiration)) {
            return false;
        }
        return claims.getSubject().equals(user.getEmail());
    }
}
