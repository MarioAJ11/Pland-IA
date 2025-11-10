package com.plandai.coreservice.exception;

/**
 * Excepción personalizada para recursos no encontrados (404)
 * Se lanza cuando se busca una entidad por ID y no existe
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String resourceName, Object id) {
        super(String.format("%s no encontrado con ID: %s", resourceName, id));
    }
}
