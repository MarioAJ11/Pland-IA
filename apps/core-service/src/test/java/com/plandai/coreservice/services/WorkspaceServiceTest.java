package com.plandai.coreservice.services;

import com.plandai.coreservice.entities.Workspace;
import com.plandai.coreservice.repositories.WorkspaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios para WorkspaceService
 * Usa Mockito para simular WorkspaceRepository
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("WorkspaceService - Lógica de Negocio de Workspaces")
class WorkspaceServiceTest {

    @Mock
    private WorkspaceRepository workspaceRepository;

    @InjectMocks
    private WorkspaceService workspaceService;

    private UUID testUserId;
    private UUID testWorkspaceId;
    private Workspace testWorkspace;

    @BeforeEach
    void setUp() {
        testUserId = UUID.randomUUID();
        testWorkspaceId = UUID.randomUUID();
        
        testWorkspace = new Workspace();
        testWorkspace.setId(testWorkspaceId);
        testWorkspace.setUserId(testUserId);
        testWorkspace.setName("Test Workspace");
        testWorkspace.setDescription("Test Description");
    }

    @Test
    @DisplayName("Debe obtener todos los workspaces de un usuario")
    void testGetWorkspacesByUserId() {
        // Arrange
        Workspace workspace2 = new Workspace();
        workspace2.setId(UUID.randomUUID());
        workspace2.setUserId(testUserId);
        workspace2.setName("Personal");
        
        List<Workspace> expectedWorkspaces = Arrays.asList(testWorkspace, workspace2);
        when(workspaceRepository.findByUserId(testUserId)).thenReturn(expectedWorkspaces);
        
        // Act
        List<Workspace> result = workspaceService.getWorkspacesByUserId(testUserId);
        
        // Assert
        assertNotNull(result, "Result no debe ser null");
        assertEquals(2, result.size(), "Debe retornar 2 workspaces");
        assertEquals("Test Workspace", result.get(0).getName());
        assertEquals("Personal", result.get(1).getName());
        verify(workspaceRepository, times(1)).findByUserId(testUserId);
    }

    @Test
    @DisplayName("Debe retornar lista vacía cuando usuario no tiene workspaces")
    void testGetWorkspacesByUserId_Empty() {
        // Arrange
        when(workspaceRepository.findByUserId(testUserId)).thenReturn(Arrays.asList());
        
        // Act
        List<Workspace> result = workspaceService.getWorkspacesByUserId(testUserId);
        
        // Assert
        assertNotNull(result, "Result no debe ser null");
        assertTrue(result.isEmpty(), "Debe retornar lista vacía");
        verify(workspaceRepository, times(1)).findByUserId(testUserId);
    }

    @Test
    @DisplayName("Debe obtener workspace por ID correctamente")
    void testGetWorkspaceById() {
        // Arrange
        when(workspaceRepository.findById(testWorkspaceId)).thenReturn(Optional.of(testWorkspace));
        
        // Act
        Workspace result = workspaceService.getWorkspaceById(testWorkspaceId);
        
        // Assert
        assertNotNull(result, "Result no debe ser null");
        assertEquals(testWorkspaceId, result.getId());
        assertEquals("Test Workspace", result.getName());
        verify(workspaceRepository, times(1)).findById(testWorkspaceId);
    }

    @Test
    @DisplayName("Debe lanzar RuntimeException cuando workspace no existe")
    void testGetWorkspaceById_NotFound() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        when(workspaceRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            workspaceService.getWorkspaceById(nonExistentId);
        });
        
        assertEquals("Workspace no encontrado", exception.getMessage());
        verify(workspaceRepository, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("Debe crear workspace correctamente")
    void testCreateWorkspace() {
        // Arrange
        Workspace newWorkspace = new Workspace();
        newWorkspace.setUserId(testUserId);
        newWorkspace.setName("New Workspace");
        newWorkspace.setDescription("New Description");
        
        when(workspaceRepository.save(any(Workspace.class))).thenReturn(testWorkspace);
        
        // Act
        Workspace result = workspaceService.createWorkspace(newWorkspace);
        
        // Assert
        assertNotNull(result, "Result no debe ser null");
        verify(workspaceRepository, times(1)).save(newWorkspace);
    }

    @Test
    @DisplayName("Debe actualizar workspace existente")
    void testUpdateWorkspace() {
        // Arrange
        Workspace updatedWorkspace = new Workspace();
        updatedWorkspace.setId(testWorkspaceId);
        updatedWorkspace.setUserId(testUserId);
        updatedWorkspace.setName("Updated Name");
        updatedWorkspace.setDescription("Updated Description");
        
        when(workspaceRepository.findById(testWorkspaceId)).thenReturn(Optional.of(testWorkspace));
        when(workspaceRepository.save(any(Workspace.class))).thenReturn(updatedWorkspace);
        
        // Act
        Workspace result = workspaceService.updateWorkspace(testWorkspaceId, updatedWorkspace);
        
        // Assert
        assertNotNull(result, "Result no debe ser null");
        verify(workspaceRepository, times(1)).findById(testWorkspaceId);
        verify(workspaceRepository, times(1)).save(any(Workspace.class));
    }

    @Test
    @DisplayName("Debe lanzar RuntimeException al actualizar workspace inexistente")
    void testUpdateWorkspace_NotFound() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        Workspace updatedWorkspace = new Workspace();
        updatedWorkspace.setName("Updated");
        
        when(workspaceRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            workspaceService.updateWorkspace(nonExistentId, updatedWorkspace);
        });
        
        assertEquals("Workspace no encontrado", exception.getMessage());
        verify(workspaceRepository, times(1)).findById(nonExistentId);
        verify(workspaceRepository, never()).save(any(Workspace.class));
    }

    @Test
    @DisplayName("Debe eliminar workspace correctamente")
    void testDeleteWorkspace() {
        // Arrange
        when(workspaceRepository.existsById(testWorkspaceId)).thenReturn(true);
        doNothing().when(workspaceRepository).deleteById(testWorkspaceId);
        
        // Act
        workspaceService.deleteWorkspace(testWorkspaceId);
        
        // Assert
        verify(workspaceRepository, times(1)).existsById(testWorkspaceId);
        verify(workspaceRepository, times(1)).deleteById(testWorkspaceId);
    }

    @Test
    @DisplayName("Debe lanzar RuntimeException al eliminar workspace inexistente")
    void testDeleteWorkspace_NotFound() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        when(workspaceRepository.existsById(nonExistentId)).thenReturn(false);
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            workspaceService.deleteWorkspace(nonExistentId);
        });
        
        assertEquals("Workspace no encontrado", exception.getMessage());
        verify(workspaceRepository, times(1)).existsById(nonExistentId);
        verify(workspaceRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    @DisplayName("Debe manejar null userId en getWorkspacesByUserId")
    void testGetWorkspacesByUserId_NullUserId() {
        // Arrange
        when(workspaceRepository.findByUserId(null)).thenReturn(Arrays.asList());
        
        // Act
        List<Workspace> result = workspaceService.getWorkspacesByUserId(null);
        
        // Assert
        assertNotNull(result, "Result no debe ser null");
        assertTrue(result.isEmpty(), "Debe retornar lista vacía para null userId");
    }

    @Test
    @DisplayName("Debe actualizar campos en updateWorkspace")
    void testUpdateWorkspace_UpdatesFields() {
        // Arrange
        Workspace updatedWorkspace = new Workspace();
        updatedWorkspace.setName("Updated Name");
        updatedWorkspace.setDescription("Updated Description");
        
        when(workspaceRepository.findById(testWorkspaceId)).thenReturn(Optional.of(testWorkspace));
        when(workspaceRepository.save(any(Workspace.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // Act
        Workspace result = workspaceService.updateWorkspace(testWorkspaceId, updatedWorkspace);
        
        // Assert
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals("Updated Description", result.getDescription());
    }
}
