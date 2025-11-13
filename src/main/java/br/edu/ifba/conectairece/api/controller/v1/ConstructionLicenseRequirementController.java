package br.edu.ifba.conectairece.api.controller.v1;

import java.util.List;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.ConstructionLicenseRequirementFinalizeRequestDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response.ConstructionLicenseRequirementFinalizeResponseDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response.ConstructionLicenseRequirementFinalizedDetailDTO;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.ConstructionLicenseRequirementRequestDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.ConstructionLicenseRequirementUpdateRequestDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response.ConstructionLicenseRequirementDetailDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response.ConstructionLicenseRequirementResponseDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.service.IConstructionLicenseRequirementService;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.util.ResultError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
 * Provides operations to create, list, retrieve, and delete construction
 * license requirements.
 *
 * Endpoints:
 * - POST
 * /api/v1/construction-license-requirements/construction-license-requirement →
 * Create a new construction license requirement.
 * - GET /api/v1/construction-license-requirements → List all construction
 * license requirements.
 * - GET /api/v1/construction-license-requirements/{id} → Retrieve a specific
 * requirement by ID.
 * - DELETE /api/v1/construction-license-requirements/{id} → Delete a
 * requirement by ID.
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

        private final IConstructionLicenseRequirementService service;

        /**
         * Endpoint to create a new construction license requirement.
         *
         * @param dto DTO containing requirement data
         * @return response with created requirement
         * @author Caio Alves
         */
        @Operation(summary = "Create a new Construction License Requirement", description = "Creates and persists a new construction license requirement in the system.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Requirement successfully created", content = @Content(schema = @Schema(implementation = ConstructionLicenseRequirementResponseDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid request body"),
                        @ApiResponse(responseCode = "422", description = "One or some fields are invalid")
        })
        @PostMapping(path = "/construction-license-requirement", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> create(
                        @RequestBody @Valid ConstructionLicenseRequirementRequestDTO dto, BindingResult result) {
                return result.hasErrors()
                                ? ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                                .body(ResultError.getResultErrors(result))
                                : ResponseEntity.ok(service.save(dto));
        }

        /**
         * Endpoint to update a construction license requirement by ID.
         *
         * @param id  requirement ID
         * @param dto DTO containing updated requirement data
         * @return updated requirement
         * @author Caio Alves
         */
        @Operation(summary = "Update a Construction License Requirement by ID", description = "Updates and persists changes to a construction license requirement.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Requirement successfully updated", content = @Content(schema = @Schema(implementation = ConstructionLicenseRequirementResponseDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid request body"),
                        @ApiResponse(responseCode = "404", description = "Requirement not found"),
                        @ApiResponse(responseCode = "422", description = "One or some fields are invalid")
        })
        @PutMapping(value = "/construction-license-requirement/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> update(
                        @PathVariable @NotNull Long id,
                        @RequestBody @Valid ConstructionLicenseRequirementUpdateRequestDTO dto, BindingResult result) {
                return result.hasErrors()
                                ? ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                                .body(ResultError.getResultErrors(result))
                                : ResponseEntity.ok(service.update(id, dto));
        }

        /**
         * Endpoint to list all construction license requirements.
         *
         * @return list of requirements
         * @author Caio Alves
         */
        @Operation(summary = "List all Construction License Requirements", description = "Retrieves a list of all registered construction license requirements.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "List of requirements retrieved", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConstructionLicenseRequirementResponseDTO.class))))
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
         * @author Caio Alves
         */
        @Operation(summary = "Retrieve a Construction License Requirement by ID", description = "Fetches details of a requirement by its ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Requirement found", content = @Content(schema = @Schema(implementation = ConstructionLicenseRequirementResponseDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Requirement not found")
        })
        @GetMapping(value = "/construction-license-requirement/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ConstructionLicenseRequirementDetailDTO> getById(
                        @PathVariable @NotNull Long id) {
                return ResponseEntity.ok(service.findById(id));
        }

        /**
         * Endpoint to delete a requirement by ID.
         *
         * @param id requirement ID
         * @return no content if deletion is successful
         * @author Caio Alves
         */
        @Operation(summary = "Delete a Construction License Requirement by ID", description = "Deletes a construction license requirement by its ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Requirement successfully deleted"),
                        @ApiResponse(responseCode = "404", description = "Requirement not found")
        })
        @DeleteMapping(value = "/construction-license-requirement/{id}")
        public ResponseEntity<Void> delete(@PathVariable @NotNull Long id) {
                service.delete(id);
                return ResponseEntity.noContent().build();
        }

        /**
         * Endpoint to retrieve a paginated list of construction license requirements
         * filtered by the associated RequirementType name.
         * The type name is provided as a query parameter.
         *
         * @param typeName The RequirementType name to filter by (passed as query
         *                 parameter ?typeName=...).
         * @param pageable Pagination and sorting information provided by Spring Web.
         * @return A ResponseEntity containing a Page of
         *         ConstructionLicenseRequirementResponseDTO.
         * @author Caio Alves
         */
        @Operation(summary = "List Construction License Requirements by Type Name", description = "Retrieves a paginated list of construction license requirements filtered by the name of their associated RequirementType.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Paginated list of requirements retrieved successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid type name or pagination parameters"),
                        @ApiResponse(responseCode = "404", description = "Requirement not found")
        })
        @GetMapping(path = "/by-type", params = "typeName", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Page<ConstructionLicenseRequirementResponseDTO>> getRequirementsByTypeName(
                        @RequestParam String typeName,

                        @ParameterObject Pageable pageable) {
                Page<ConstructionLicenseRequirementResponseDTO> requirements = service
                                .findByRequirementTypeName(typeName, pageable);
                return ResponseEntity.ok(requirements);
        }

        /**
         * Endpoint for the Public Servant to officially approve a Construction License
         * Requirement.
         * This action finalizes the requirement's status to ACCEPTED and records the
         * justification (Comment).
         *
         * @param constructionLicenseRequirementId The ID of the requirement to be
         *                                         approved.
         * @param dto                              DTO containing the Public Servant ID
         *                                         and the acceptance note/comment.
         * @param result                           Validation binding result.
         * @return Response DTO with the finalized requirement details (Status 200 OK).
         */
        @Operation(summary = "Approve Construction License Requirement", description = "Finalizes the review process by the Public Servant, setting the Requirement status to ACCEPTED and recording the justification/comment.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Requirement successfully approved", content = @Content(schema = @Schema(implementation = ConstructionLicenseRequirementFinalizeResponseDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid request body or missing required fields"),
                        @ApiResponse(responseCode = "404", description = "Requirement or Public Servant profile not found"),
                        @ApiResponse(responseCode = "409", description = "Workflow Conflict: Technical Responsible approval/association is still PENDING or missing."),
                        @ApiResponse(responseCode = "422", description = "Validation error in fields or If 3 or more documents are not approved")
        })
        @PostMapping(path = "/construction-license-requirement/{constructionLicenseRequirementId}/approve")
        public ResponseEntity<?> approveConstructionLicenseRequirement(
                        @PathVariable("constructionLicenseRequirementId") Long constructionLicenseRequirementId,
                        @RequestBody @Valid ConstructionLicenseRequirementFinalizeRequestDTO dto,
                        BindingResult result) {
                return result.hasErrors()
                                ? ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                                .body(ResultError.getResultErrors(result))
                                : ResponseEntity.ok(service.approveConstructionLicenseRequirement(
                                                constructionLicenseRequirementId, dto));
        }

        /**
         * Endpoint for the Public Servant to officially reject a Construction License
         * Requirement.
         * This action finalizes the requirement's status to REJECTED and records the
         * mandatory justification (Comment).
         *
         * @param constructionLicenseRequirementId The ID of the requirement to be
         *                                         rejected.
         * @param dto                              DTO containing the Public Servant ID
         *                                         and the mandatory rejection comment.
         * @param result                           Validation binding result.
         * @return Response DTO with the finalized requirement details (Status 200 OK).
         */
        @Operation(summary = "Reject Construction License Requirement", description = "Finalizes the review process by the Public Servant, setting the Requirement status to REJECTED and recording the mandatory justification/comment.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Requirement successfully rejected", content = @Content(schema = @Schema(implementation = ConstructionLicenseRequirementFinalizeResponseDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid request body or missing required fields"),
                        @ApiResponse(responseCode = "404", description = "Requirement or Public Servant profile not found"),
                        @ApiResponse(responseCode = "409", description = "Workflow Conflict: Technical Responsible approval/association is still PENDING or missing."),
                        @ApiResponse(responseCode = "422", description = "Validation error in fields")
        })
        @PostMapping(path = "/construction-license-requirement/{constructionLicenseRequirementId}/rejected")
        public ResponseEntity<?> rejectedConstructionLicenseRequirement(
                        @PathVariable("constructionLicenseRequirementId") Long constructionLicenseRequirementId,
                        @RequestBody @Valid ConstructionLicenseRequirementFinalizeRequestDTO dto,
                        BindingResult result) {
                return result.hasErrors()
                                ? ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                                .body(ResultError.getResultErrors(result))
                                : ResponseEntity.ok(service.rejectConstructionLicenseRequirement(
                                                constructionLicenseRequirementId, dto));
        }

        /**
         * Retrieves the finalized details of a Construction License Requirement,
         * including
         * the final justification issued by the Public Servant after review (Approved
         * or Rejected).
         *
         * <p>
         * This endpoint should be used to fetch the complete review outcome for
         * finalized
         * construction license requirements, providing both administrative and
         * technical details.
         * </p>
         *
         * <p>
         * <b>Use Case:</b> Displaying final results to the applicant or for
         * administrative audits.
         * </p>
         *
         * @param id the unique identifier of the finalized Construction License
         *           Requirement; must not be null
         * @return {@link ResponseEntity} containing the finalized requirement details,
         *         including justification
         *
         * @throws BusinessException if the requirement does not exist or is not
         *                           finalized
         *
         * @see ConstructionLicenseRequirementFinalizedDetailDTO
         * @author Andesson Reis
         */
        @Operation(summary = "Retrieve Finalized Construction License Requirement Details", description = """
                        Retrieves detailed information of a finalized Construction License Requirement (Approved or Rejected),
                        including the final justification provided by the Public Servant.
                        This endpoint is intended for review visualization and audit purposes.
                        """)
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Finalized requirement details retrieved successfully.", content = @Content(schema = @Schema(implementation = ConstructionLicenseRequirementFinalizedDetailDTO.class))),
                        @ApiResponse(responseCode = "404", description = "No finalized requirement found for the provided ID.")
        })
        @GetMapping(value = "/construction-license-requirement/{id}/review-details", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ConstructionLicenseRequirementFinalizedDetailDTO> getFinalizedById(
                        @Parameter(description = "Unique identifier of the finalized Construction License Requirement", required = true) @PathVariable @NotNull Long id) {

                ConstructionLicenseRequirementFinalizedDetailDTO finalizedRequirement = service.findFinalizedById(id);
                return ResponseEntity.ok(finalizedRequirement);
        }

}
