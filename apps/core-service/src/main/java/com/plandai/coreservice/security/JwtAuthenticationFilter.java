package com.plandai.coreservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Filtro que intercepta todas las peticiones HTTP para validar JWT
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extraer el token del header Authorization
        String authorizationHeader = request.getHeader("Authorization");

        String token = null;
        String userId = null;

        // Verificar que el header existe y tiene formato correcto
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // Remover "Bearer " prefix
            try {
                userId = jwtUtil.extractUserId(token);
                logger.info("✅ UserId extraído del JWT: " + userId);
            } catch (Exception e) {
                logger.error("❌ Error extrayendo userId del token: " + e.getMessage(), e);
            }
        }

        // Si tenemos userId y no hay autenticación previa, validar el token
        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            if (jwtUtil.validateToken(token)) {
                // Extraer información adicional del token
                String email = jwtUtil.extractEmail(token);
                String name = jwtUtil.extractName(token);

                // Crear objeto de autenticación
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
                
                // Agregar detalles adicionales
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Establecer autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
                
                logger.debug("JWT válido para userId: " + userId + " (" + email + ")");
            } else {
                logger.warn("Token JWT inválido o expirado");
            }
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
