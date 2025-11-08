package com.plandai.coreservice.integration;

import com.plandai.coreservice.dto.WorkspaceCreateDto;
import com.plandai.coreservice.dto.WorkspaceUpdateDto;
import com.plandai.coreservice.entities.Workspace;
import com.plandai.coreservice.repositories.WorkspaceRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de integración para WorkspaceController
 * Prueban el flujo completo: Controller -> Service -> Repository -> Database
 */
@DisplayName("Workspace API - Integration Tests")
class WorkspaceIntegrationTest extends IntegrationTestBase {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        workspaceRepository.deleteAll();
    }

    @Test
    @DisplayName("Debe crear un workspace correctamente")
    void shouldCreateWorkspace() {
        WorkspaceCreateDto createDto = new WorkspaceCreateDto();
        createDto.setName("Mi Workspace");
        createDto.setDescription("Descripción de prueba");
        createDto.setUserId(UUID.randomUUID());

        given()
                .contentType(ContentType.JSON)
                .body(createDto)
                .when()
                .post(getBaseUrl() + "/api/workspaces")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("Mi Workspace"))
                .body("description", equalTo("Descripción de prueba"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue())
                .body("updatedAt", notNullValue());

        assertEquals(1, workspaceRepository.count());
    }

    @Test
    @DisplayName("Debe obtener workspace por ID")
    void shouldGetWorkspaceById() {
        Workspace workspace = new Workspace();
        workspace.setName("Test Workspace");
        workspace.setDescription("Test Description");
        workspace.setUserId(UUID.randomUUID());
        Workspace saved = workspaceRepository.save(workspace);

        given()
                .when()
                .get(getBaseUrl() + "/api/workspaces/" + saved.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(saved.getId().toString()))
                .body("name", equalTo("Test Workspace"))
                .body("description", equalTo("Test Description"));
    }

    @Test
    @DisplayName("Debe retornar 404 cuando workspace no existe")
    void shouldReturn404WhenWorkspaceNotFound() {
        UUID nonExistentId = UUID.randomUUID();

        given()
                .when()
                .get(getBaseUrl() + "/api/workspaces/" + nonExistentId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Debe obtener todos los workspaces de un usuario")
    void shouldGetAllWorkspacesByUserId() {
        UUID userId = UUID.randomUUID();

        Workspace workspace1 = new Workspace();
        workspace1.setName("Workspace 1");
        workspace1.setUserId(userId);
        workspaceRepository.save(workspace1);

        Workspace workspace2 = new Workspace();
        workspace2.setName("Workspace 2");
        workspace2.setUserId(userId);
        workspaceRepository.save(workspace2);

        Workspace otherUserWorkspace = new Workspace();
        otherUserWorkspace.setName("Other User Workspace");
        otherUserWorkspace.setUserId(UUID.randomUUID());
        workspaceRepository.save(otherUserWorkspace);

        given()
                .queryParam("userId", userId.toString())
                .when()
                .get(getBaseUrl() + "/api/workspaces")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", equalTo(2))
                .body("name", hasItems("Workspace 1", "Workspace 2"));
    }

    @Test
    @DisplayName("Debe actualizar un workspace")
    void shouldUpdateWorkspace() {
        Workspace workspace = new Workspace();
        workspace.setName("Original Name");
        workspace.setDescription("Original Description");
        workspace.setUserId(UUID.randomUUID());
        Workspace saved = workspaceRepository.save(workspace);

        WorkspaceUpdateDto updateDto = new WorkspaceUpdateDto();
        updateDto.setName("Updated Name");
        updateDto.setDescription("Updated Description");

        given()
                .contentType(ContentType.JSON)
                .body(updateDto)
                .when()
                .put(getBaseUrl() + "/api/workspaces/" + saved.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Updated Name"))
                .body("description", equalTo("Updated Description"));

        Workspace updated = workspaceRepository.findById(saved.getId()).orElseThrow();
        assertEquals("Updated Name", updated.getName());
        assertEquals("Updated Description", updated.getDescription());
    }

    @Test
    @DisplayName("Debe eliminar un workspace")
    void shouldDeleteWorkspace() {
        Workspace workspace = new Workspace();
        workspace.setName("To Delete");
        workspace.setUserId(UUID.randomUUID());
        Workspace saved = workspaceRepository.save(workspace);

        given()
                .when()
                .delete(getBaseUrl() + "/api/workspaces/" + saved.getId())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        assertFalse(workspaceRepository.existsById(saved.getId()));
    }

    @Test
    @DisplayName("Debe validar nombre mínimo de 3 caracteres")
    void shouldValidateMinimumNameLength() {
        WorkspaceCreateDto createDto = new WorkspaceCreateDto();
        createDto.setName("AB");
        createDto.setUserId(UUID.randomUUID());

        given()
                .contentType(ContentType.JSON)
                .body(createDto)
                .when()
                .post(getBaseUrl() + "/api/workspaces")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        assertEquals(0, workspaceRepository.count());
    }

    @Test
    @DisplayName("Debe validar nombre requerido")
    void shouldValidateNameRequired() {
        WorkspaceCreateDto createDto = new WorkspaceCreateDto();
        createDto.setUserId(UUID.randomUUID());

        given()
                .contentType(ContentType.JSON)
                .body(createDto)
                .when()
                .post(getBaseUrl() + "/api/workspaces")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        assertEquals(0, workspaceRepository.count());
    }

    @Test
    @DisplayName("Debe validar userId requerido")
    void shouldValidateUserIdRequired() {
        WorkspaceCreateDto createDto = new WorkspaceCreateDto();
        createDto.setName("Test Workspace");

        given()
                .contentType(ContentType.JSON)
                .body(createDto)
                .when()
                .post(getBaseUrl() + "/api/workspaces")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        assertEquals(0, workspaceRepository.count());
    }
}
