package com.livedocs.server.webapp.services;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.livedocs.server.webapp.entity.Users;
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

    @Deprecated
    public String generateJwtToken(Users user) {
        Map<String, Object> map = addClaims(user);
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS256");
        headers.put("typ", "JWT");
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(3, ChronoUnit.DAYS);

        String token = Jwts.builder()
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiration))
                .setHeader(headers)
                .addClaims(map)
                .signWith(Keys.hmacShaKeyFor(readKeyFile(privateKeyPath).getBytes()))
                .compact();
        return token;
    }

    private Map<String, Object> addClaims(Users user) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", user.getName());
        userMap.put("age", user.getAge());
        userMap.put("country", user.getCountry());
        userMap.put("email", user.getEmail());
        return userMap;
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

    public boolean verifyJwtToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(readKeyFile(publicKeyPath).getBytes()))
                .build()
                .parseSignedClaims(token).getPayload();
        Date expiration = claims.getExpiration();
        if (!checkExpiration(expiration)) {
            return false;
        }
        return true;
    }
}
