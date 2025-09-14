package br.edu.ifba.conectairece.api.controller.v1;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.ConstructionLicenseRequirementRequestDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response.ConstructionLicenseRequirementResponseDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.service.ConstructionLicenseRequirementIService;
import br.edu.ifba.conectairece.api.infraestructure.util.ResultError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

/**
 * Controller responsible for handling ConstructionLicenseRequirement endpoints.
 * Provides operations to create, list, retrieve, and delete construction license requirements.
 *
 * Endpoints:
 * - POST   /api/v1/construction-license-requirements/construction-license-requirement → Create a new construction license requirement.
 * - GET    /api/v1/construction-license-requirements                              → List all construction license requirements.
 * - GET    /api/v1/construction-license-requirements/{id}                         → Retrieve a specific requirement by ID.
 * - DELETE /api/v1/construction-license-requirements/{id}                         → Delete a requirement by ID.
 *
 * This controller acts as the entry point for managing construction license
 * requests, exposing operations for persistence and retrieval through DTOs.
 *
 * @author Caio Alves
 */

@RestController
@RequestMapping("/api/v1/construction-license-requirements")
@RequiredArgsConstructor
public class ConstructionLicenseRequirementController {

    private final ConstructionLicenseRequirementIService service;

    /**
     * Endpoint to create a new construction license requirement.
     *
     * @param dto DTO containing requirement data
     * @return response with created requirement
     */
     @Operation(summary = "Create a new Construction License Requirement",
            description = "Creates and persists a new construction license requirement in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requirement successfully created",
                    content = @Content(schema = @Schema(implementation = ConstructionLicenseRequirementResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid")
    })
    @PostMapping(path = "/construction-license-requirement" ,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(
            @RequestBody @Valid ConstructionLicenseRequirementRequestDTO dto, BindingResult result) {
        return result.hasErrors()
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result))
                :ResponseEntity.ok(service.save(dto));
    }

    /**
         * Endpoint to update a construction license requirement by ID.
        *
         * @param id  requirement ID
        * @param dto DTO containing updated requirement data
         * @return updated requirement
     */
        @Operation(summary = "Update a Construction License Requirement by ID",
        description = "Updates and persists changes to a construction license requirement.")
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Requirement successfully updated",
                content = @Content(schema = @Schema(implementation = ConstructionLicenseRequirementResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request body"),
        @ApiResponse(responseCode = "404", description = "Requirement not found"),
        @ApiResponse(responseCode = "422", description = "One or some fields are invalid")
        })
        @PutMapping(value = "/construction-license-requirement/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
                public ResponseEntity<?> update(
                @PathVariable @NotNull Long id,
                @RequestBody @Valid ConstructionLicenseRequirementRequestDTO dto, BindingResult result) {
        return result.hasErrors()
                        ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result))
                        : ResponseEntity.ok(service.update(id, dto));
        }

    /**
     * Endpoint to list all construction license requirements.
     *
     * @return list of requirements
     */
        @Operation(summary = "List all Construction License Requirements",
            description = "Retrieves a list of all registered construction license requirements.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of requirements retrieved",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConstructionLicenseRequirementResponseDTO.class))))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConstructionLicenseRequirementResponseDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Endpoint to get a requirement by ID.
     *
     * @param id requirement ID
     * @return response with requirement data
     */
    @Operation(summary = "Retrieve a Construction License Requirement by ID",
            description = "Fetches details of a requirement by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requirement found",
                    content = @Content(schema = @Schema(implementation = ConstructionLicenseRequirementResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Requirement not found")
    })
    @GetMapping(value = "/construction-license-requirement/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConstructionLicenseRequirementResponseDTO> getById(
            @PathVariable @NotNull Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Endpoint to delete a requirement by ID.
     *
     * @param id requirement ID
     * @return no content if deletion is successful
     */
    @Operation(summary = "Delete a Construction License Requirement by ID",
            description = "Deletes a construction license requirement by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Requirement successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Requirement not found")
    })
    @DeleteMapping(value = "/construction-license-requirement/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
