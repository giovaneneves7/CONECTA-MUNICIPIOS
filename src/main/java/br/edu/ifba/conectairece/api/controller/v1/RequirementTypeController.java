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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.conectairece.api.features.requirementType.domain.dto.request.RequirementTypeRequestDTO;
import br.edu.ifba.conectairece.api.features.requirementType.domain.dto.response.RequirementTypeResponseDTO;
import br.edu.ifba.conectairece.api.features.requirementType.domain.service.RequirementTypeIService;
import br.edu.ifba.conectairece.api.infraestructure.util.ResultError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller responsible for handling RequirementType endpoints.
 * Provides operations to create, list, retrieve, and delete requirement types.
 *
 * Endpoints:
 * - POST   /api/v1/requirement-types/requirement-type → Create a new requirement type.
 * - GET    /api/v1/requirement-types                  → List all requirement types.
 * - GET    /api/v1/requirement-types/{id}             → Retrieve a specific requirement type by ID.
 * - DELETE /api/v1/requirement-types/{id}             → Delete a requirement type by ID.
 *
 * This controller exposes operations for managing classification
 * of requirements, ensuring consistency in construction license processes.
 *
 * Authors: Caio Alves
 */

@RestController
@RequestMapping("/api/v1/requirement-types")
@RequiredArgsConstructor
public class RequirementTypeController {

    private final RequirementTypeIService requirementTypeService;

    /**
     * Endpoint to create a new requirement type.
     *
     * @param dto DTO containing requirement type data.
     * @return Response with created requirement type data.
     */
    @Operation(summary = "Create a new Requirement Type",
            description = "Creates and persists a new requirement type in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requirement type successfully created",
                    content = @Content(schema = @Schema(implementation = RequirementTypeResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid")
    })
    @PostMapping(path = "/requirement-type", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(
            @RequestBody @Valid RequirementTypeRequestDTO dto, BindingResult result) {
        return result.hasErrors()
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result))
                : ResponseEntity.ok(requirementTypeService.save(dto));
    }

    /**
     * Endpoint to list all requirement types.
     *
     * @return List of all registered requirement types.
     */
    @Operation(summary = "List all Requirement Types",
            description = "Retrieves a list of all registered requirement types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of requirement types retrieved",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = RequirementTypeResponseDTO.class))))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RequirementTypeResponseDTO>> getAll() {
        return ResponseEntity.ok(requirementTypeService.findAll());
    }

    /**
     * Endpoint to get a requirement type by ID.
     *
     * @param id Requirement type ID.
     * @return Requirement type DTO if found.
     */
    @Operation(summary = "Retrieve a Requirement Type by ID",
            description = "Fetches details of a requirement type by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requirement type found",
                    content = @Content(schema = @Schema(implementation = RequirementTypeResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Requirement type not found")
    })
    @GetMapping(value =  "/requirement-type/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequirementTypeResponseDTO> getById(@PathVariable @Valid Long id) {
        return ResponseEntity.ok(requirementTypeService.findById(id));
    }

    /**
     * Endpoint to delete a requirement type by ID.
     *
     * @param id Requirement type ID.
     * @return No content if deletion is successful.
     */
    @Operation(summary = "Delete a Requirement Type by ID",
            description = "Deletes a requirement type by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Requirement type successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Requirement type not found")
    })
    @DeleteMapping(value = "/requirement-type/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Valid Long id) {
        requirementTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
