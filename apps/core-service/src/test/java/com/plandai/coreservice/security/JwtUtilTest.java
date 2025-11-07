package com.plandai.coreservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para JwtUtil
 * Valida: extracción de claims, validación de tokens, manejo de errores
 */
@DisplayName("JwtUtil - Validación y Extracción de JWT Tokens")
class JwtUtilTest {

    private JwtUtil jwtUtil;
    private String testSecret = "CAMBIAME_POR_UN_SECRET_SEGURO_DE_AL_MENOS_32_CARACTERES";
    private String testIssuer = "PlandIA.AuthService";
    private String testAudience = "PlandIA.Clients";
    private String testUserId = UUID.randomUUID().toString();
    private String testEmail = "test@plandai.com";
    private String testName = "Test User";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        
        // Inyectar valores de prueba usando ReflectionTestUtils
        ReflectionTestUtils.setField(jwtUtil, "secret", testSecret);
        ReflectionTestUtils.setField(jwtUtil, "issuer", testIssuer);
        ReflectionTestUtils.setField(jwtUtil, "audience", testAudience);
    }

    /**
     * Genera un token JWT de prueba válido
     */
    private String generateTestToken(long expirationMillis) {
        SecretKey key = Keys.hmacShaKeyFor(testSecret.getBytes(StandardCharsets.UTF_8));
        
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMillis);
        
        return Jwts.builder()
                .claim("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier", testUserId)
                .claim("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress", testEmail)
                .claim("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name", testName)
                .issuer(testIssuer)
                .audience().add(testAudience).and()
                .issuedAt(now)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    @Test
    @DisplayName("Debe extraer userId correctamente del token JWT")
    void testExtractUserId() {
        // Arrange
        String token = generateTestToken(60000); // 1 minuto
        
        // Act
        String extractedUserId = jwtUtil.extractUserId(token);
        
        // Assert
        assertNotNull(extractedUserId, "UserId no debe ser null");
        assertEquals(testUserId, extractedUserId, "UserId extraído debe coincidir");
    }

    @Test
    @DisplayName("Debe extraer email correctamente del token JWT")
    void testExtractEmail() {
        // Arrange
        String token = generateTestToken(60000);
        
        // Act
        String extractedEmail = jwtUtil.extractEmail(token);
        
        // Assert
        assertNotNull(extractedEmail, "Email no debe ser null");
        assertEquals(testEmail, extractedEmail, "Email extraído debe coincidir");
    }

    @Test
    @DisplayName("Debe extraer name correctamente del token JWT")
    void testExtractName() {
        // Arrange
        String token = generateTestToken(60000);
        
        // Act
        String extractedName = jwtUtil.extractName(token);
        
        // Assert
        assertNotNull(extractedName, "Name no debe ser null");
        assertEquals(testName, extractedName, "Name extraído debe coincidir");
    }

    @Test
    @DisplayName("Debe validar token JWT válido correctamente")
    void testValidateToken_Valid() {
        // Arrange
        String token = generateTestToken(60000); // Token válido por 1 minuto
        
        // Act
        Boolean isValid = jwtUtil.validateToken(token);
        
        // Assert
        assertTrue(isValid, "Token válido debe retornar true");
    }

    @Test
    @DisplayName("Debe rechazar token JWT expirado")
    void testValidateToken_Expired() {
        // Arrange
        String expiredToken = generateTestToken(-1000); // Token expirado hace 1 segundo
        
        // Act
        Boolean isValid = jwtUtil.validateToken(expiredToken);
        
        // Assert
        assertFalse(isValid, "Token expirado debe retornar false");
    }

    @Test
    @DisplayName("Debe rechazar token JWT con firma inválida")
    void testValidateToken_InvalidSignature() {
        // Arrange
        String token = generateTestToken(60000);
        String tamperedToken = token.substring(0, token.length() - 5) + "XXXXX"; // Modificar firma
        
        // Act
        Boolean isValid = jwtUtil.validateToken(tamperedToken);
        
        // Assert
        assertFalse(isValid, "Token con firma inválida debe retornar false");
    }

    @Test
    @DisplayName("Debe rechazar token JWT malformado")
    void testValidateToken_Malformed() {
        // Arrange
        String malformedToken = "este.no.es.un.token.valido";
        
        // Act
        Boolean isValid = jwtUtil.validateToken(malformedToken);
        
        // Assert
        assertFalse(isValid, "Token malformado debe retornar false");
    }

    @Test
    @DisplayName("Debe extraer fecha de expiración correctamente")
    void testExtractExpiration() {
        // Arrange
        long expirationMillis = 60000; // 1 minuto
        String token = generateTestToken(expirationMillis);
        
        // Act
        Date expiration = jwtUtil.extractExpiration(token);
        
        // Assert
        assertNotNull(expiration, "Expiration no debe ser null");
        assertTrue(expiration.after(new Date()), "Expiration debe estar en el futuro");
    }

    @Test
    @DisplayName("Debe lanzar excepción al extraer userId de token con issuer incorrecto")
    void testExtractUserId_WrongIssuer() {
        // Arrange
        SecretKey key = Keys.hmacShaKeyFor(testSecret.getBytes(StandardCharsets.UTF_8));
        String tokenWithWrongIssuer = Jwts.builder()
                .claim("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier", testUserId)
                .issuer("WrongIssuer")
                .audience().add(testAudience).and()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 60000))
                .signWith(key)
                .compact();
        
        // Act & Assert
        assertThrows(Exception.class, () -> {
            jwtUtil.extractUserId(tokenWithWrongIssuer);
        }, "Debe lanzar excepción con issuer incorrecto");
    }

    @Test
    @DisplayName("Debe lanzar excepción al extraer userId de token con audience incorrecto")
    void testExtractUserId_WrongAudience() {
        // Arrange
        SecretKey key = Keys.hmacShaKeyFor(testSecret.getBytes(StandardCharsets.UTF_8));
        String tokenWithWrongAudience = Jwts.builder()
                .claim("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier", testUserId)
                .issuer(testIssuer)
                .audience().add("WrongAudience").and()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 60000))
                .signWith(key)
                .compact();
        
        // Act & Assert
        assertThrows(Exception.class, () -> {
            jwtUtil.extractUserId(tokenWithWrongAudience);
        }, "Debe lanzar excepción con audience incorrecto");
    }

    @Test
    @DisplayName("Debe manejar token null correctamente")
    void testValidateToken_Null() {
        // Act
        Boolean isValid = jwtUtil.validateToken(null);
        
        // Assert
        assertFalse(isValid, "Token null debe retornar false");
    }

    @Test
    @DisplayName("Debe manejar token vacío correctamente")
    void testValidateToken_Empty() {
        // Act
        Boolean isValid = jwtUtil.validateToken("");
        
        // Assert
        assertFalse(isValid, "Token vacío debe retornar false");
    }
}
