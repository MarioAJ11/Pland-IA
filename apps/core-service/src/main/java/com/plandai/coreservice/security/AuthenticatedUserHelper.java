package com.plandai.coreservice.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Helper para obtener información del usuario autenticado desde el JWT
 */
@Component
public class AuthenticatedUserHelper {

    /**
     * Obtiene el userId del usuario autenticado actual como UUID
     * @return userId extraído del JWT, o null si no está autenticado
     */
    public UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof String) {
            String userIdStr = (String) authentication.getPrincipal();
            return UUID.fromString(userIdStr);
        }
        
        return null;
    }

    /**
     * Obtiene el userId como String (para casos donde se necesite el formato original)
     */
    public String getCurrentUserIdAsString() {
        UUID userId = getCurrentUserId();
        return userId != null ? userId.toString() : null;
    }

    /**
     * Verifica si hay un usuario autenticado
     */
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
}
