package com.mns.cda.loc_mns.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private static final long EXPIRATION_MS = 3_600_000; // 1 heure
    private SecretKey key;

    // Construit la clé HMAC à partir du secret
    // une seule fois, au démarrage, quand jwtSecret est injecté
    @PostConstruct
    void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Génère un token signé contenant l'email (subject) et le rôle.
     */
    public String generateToken(String email, String role) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date(now))
                .expiration(new Date(now + EXPIRATION_MS))
                .signWith(key)
                .compact();
    }

    /**
     * Vérifie la signature et l'expiration, puis renvoie le contenu du token.
     * @throws io.jsonwebtoken.JwtException si le token est invalide, expiré ou falsifié
     */
    public Claims parseToken(String jwt) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    /**
     * Récupération de l'email (subject) d'un token valide.
     */
    public String extractEmail(String jwt) {
        return parseToken(jwt).getSubject();
    }
}