package com.plandai.coreservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

/**
 * Utilidad para validar y extraer información de JWT tokens
 * Debe usar el MISMO secret que Auth Service
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer:PlandIA.AuthService}")
    private String issuer;

    @Value("${jwt.audience:PlandIA.Client}")
    private String audience;

    /**
     * Extrae el userId del token JWT (como String UUID)
     */
    public String extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier", String.class));
    }

    /**
     * Extrae el email del token JWT
     */
    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress", String.class));
    }

    /**
     * Extrae el nombre del token JWT
     */
    public String extractName(String token) {
        return extractClaim(token, claims -> claims.get("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name", String.class));
    }

    /**
     * Extrae la fecha de expiración
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae un claim específico del token
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todos los claims del token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .requireIssuer(issuer)
                .requireAudience(audience)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Verifica si el token ha expirado
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Valida el token JWT
     */
    public Boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Genera la clave de firma desde el secret
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
