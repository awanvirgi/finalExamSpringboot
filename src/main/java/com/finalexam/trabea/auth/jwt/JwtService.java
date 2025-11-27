package com.finalexam.trabea.auth.jwt;

import com.finalexam.trabea.auth.dto.RequestGenerateToken;
import com.finalexam.trabea.role.RolesName;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {
    @Value("${app.secret}")
    private String secretKey;

    public String generateToken(RequestGenerateToken request) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("ROLE", request.getRole().toString());
        extraClaims.put("workEmail", request.getWorkEmail());
        return generateToken(request, extraClaims);
    }

    private String generateToken(RequestGenerateToken request, Map<String, Object> extraClaims) {
        long now = System.currentTimeMillis();

        Long jwtExpiration = 24L * 60L * 60L * 1000L;
        return Jwts.builder()
                .subject(request.getWorkEmail())
                .issuer("Trabea")
                .issuedAt(new Date(now))
                .expiration(new Date(now + jwtExpiration))
                .claims(extraClaims)
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Boolean isValid(String token, String username) {
        return isEmailValid(username, token) && !isTokenExpired(token);
    }

    private Boolean isEmailValid(String username, String token) {
        return getClaims(token).getSubject().equals(username);
    }

    private Boolean isRoleValid(RolesName rolesName,String token){
        return getClaims(token).get("ROLE",String.class).equals(rolesName.name());
    }

    private Boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    public List<String> extractRoles(String token) {
        String role = getClaims(token).get("ROLE", String.class);
        return role != null ? List.of(role) : List.of();
    }


    public Date extractExpiration(String token) {
        return getClaims(token).getExpiration();
    }
}