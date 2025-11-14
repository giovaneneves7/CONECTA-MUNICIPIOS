package br.edu.ifba.conectairece.api.controller.v1;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.AssociationActionRequestDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.RejectionRequestDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response.ConstructionLicenseRequirementResponseDTO;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.service.IConstructionLicenseRequirementService;
import br.edu.ifba.conectairece.api.features.document.domain.dto.request.DocumentCorrectionSuggestionDTO;
import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentDetailResponseDTO;
import br.edu.ifba.conectairece.api.features.document.domain.service.IDocumentService;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request.TechnicalResponsibleApproveDocumentRequestDTO_TEMP;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request.TechnicalResponsibleRejectDocumentRequestDTO_TEMP;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request.TechnicalResponsibleRequestDTO_TEMP;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.response.TechnicalResponsibleResponseDTO_TEMP;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.service.ITechnicalResponsibleService;
import br.edu.ifba.conectairece.api.infraestructure.util.ResultError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller responsible for handling TechnicalResponsible endpoints.
 * Provides operations to create, list, retrieve, and delete technical
 * responsibles.
 *
 * @author Caio Alves, Andesson Reis
 */

@RestController
@RequestMapping("/api/v1/technical-responsibles")
@RequiredArgsConstructor
public class TechnicalResponsibleController {

    private final ITechnicalResponsibleService service;
    private final IConstructionLicenseRequirementService requirementService;
    private final IDocumentService documentService;

    /**
     * Endpoint to create a new Technical Responsible profile.
     * Validates the request body before processing.
     *
     * @param dto    The request body containing data for the new Technical
     *               Responsible.
     * @param result BindingResult for validation errors.
     * @return ResponseEntity with the created Technical Responsible data or
     *         validation errors.
     * @author Caio Alves
     */
    @Operation(summary = "Create new Technical Responsible", description = "Creates and persists a new technical responsible in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Technical Responsible successfully created", content = @Content(schema = @Schema(implementation = TechnicalResponsibleResponseDTO_TEMP.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid")
    })

    @PostMapping(path = "/technical-responsible")
    public ResponseEntity<?> create(@RequestBody @Valid TechnicalResponsibleRequestDTO_TEMP dto, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result));
        }

        TechnicalResponsibleResponseDTO_TEMP responseDto = service.save(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    /**
     * Endpoint to retrieve a list of all registered Technical Responsibles.
     *
     * @return ResponseEntity containing a list of Technical Responsible DTOs.
     * @author Caio Alves
     */
    @Operation(summary = "List all Technical Responsibles", description = "Retrieves a list of all registered technical responsibles.")
    @GetMapping
    public ResponseEntity<List<TechnicalResponsibleResponseDTO_TEMP>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Endpoint to delete a Technical Responsible profile by its unique ID.
     *
     * @param id The UUID of the Technical Responsible profile to delete.
     * @return ResponseEntity with status 204 (No Content) upon successful deletion.
     * @author Caio Alves
     */
    @Operation(summary = "Delete a Technical Responsible by ID", description = "Deletes a technical responsible from the system by its ID.")
    @DeleteMapping("/technical-responsible/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint for a Technical Responsible to accept association with a
     * requirement.
     * Requires authorization check to ensure the user matches the responsible ID.
     *
     * @param dto DTO containing the requirement and responsible IDs.
     * @return ResponseEntity with status 204 (No Content) on success.
     * @author Caio Alves
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Association accepted successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - User is not the designated responsible"),
            @ApiResponse(responseCode = "404", description = "Requirement or Responsible not found")
    })
    @PostMapping("/accept-requirement")
    public ResponseEntity<Void> acceptRequirement(@RequestBody @Valid AssociationActionRequestDTO dto) {

        requirementService.approveAssociation(dto);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint for a Technical Responsible to refuse association with a
     * requirement.
     * Requires authorization check and a justification in the request body.
     *
     * @param dto DTO containing the requirement/responsible IDs and justification.
     * @return ResponseEntity with status 204 (No Content) on success.
     * @author Caio Alves
     */
    @Operation(summary = "Refuse a requirement association", description = "Allows a technical responsible to refuse being linked to a construction license requirement.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Association refused successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - User is not the designated responsible"),
            @ApiResponse(responseCode = "404", description = "Requirement or Responsible not found")
    })
    @PostMapping("/refuse-requirement")
    public ResponseEntity<?> refuseRequirement(
            @RequestBody @Valid RejectionRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(requirementService.rejectAssociation(dto));

    }

    /**
     * Endpoint to retrieve a Technical Responsible by their unique registration ID.
     *
     * @param registrationId The registration ID (String) of the Technical
     *                       Responsible.
     * @return ResponseEntity with the Technical Responsible data if found, or 404
     *         Not Found.
     * @author Caio Alves
     */
    @Operation(summary = "Retrieve a Technical Responsible by Registration ID", description = "Fetches details of a technical responsible by its unique registration ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Technical Responsible found", content = @Content(schema = @Schema(implementation = TechnicalResponsibleResponseDTO_TEMP.class))),
            @ApiResponse(responseCode = "404", description = "Technical Responsible not found with the given registration ID")
    })
    @GetMapping("technical-responsible/{registrationId}")
    public ResponseEntity<?> getByRegistrationId(@PathVariable String registrationId) {
        return service.findByRegistrationId(registrationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to list all Construction License Requirements associated with a
     * specific Technical Responsible ID (UUID).
     *
     * @param responsibleId The UUID of the Technical Responsible.
     * @return ResponseEntity containing a list of requirement DTOs.
     * @author Caio Alves
     */
    @Operation(summary = "List all requirements for a specific Technical Responsible", description = "Retrieves a list of all construction license requirements associated with a given technical responsible ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of requirements retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Technical Responsible not found")
    })
    @GetMapping("/{responsibleId}/requirements")
    public ResponseEntity<List<ConstructionLicenseRequirementResponseDTO>> getRequirementsByResponsibleId(
            @PathVariable UUID responsibleId) {

        List<ConstructionLicenseRequirementResponseDTO> requirements = requirementService
                .findAllByTechnicalResponsible(responsibleId);
        return ResponseEntity.ok(requirements);
    }

    /**
     * Endpoint to list all Construction License Requirements associated with a
     * specific Technical Responsible, identified by their registration ID.
     *
     * @param registrationId The registration ID (String) of the Technical
     *                       Responsible.
     * @return ResponseEntity containing a list of requirement DTOs.
     * @author Caio Alves
     */
    @Operation(summary = "List all requirements by Technical Responsible's Registration ID", description = "Retrieves a list of all construction license requirements associated with a given registration ID.")
    @GetMapping("/registration/{registrationId}/requirements")
    public ResponseEntity<List<ConstructionLicenseRequirementResponseDTO>> getRequirementsByRegistrationId(
            @PathVariable String registrationId) {
        List<ConstructionLicenseRequirementResponseDTO> requirements = requirementService
                .findAllByTechnicalResponsibleRegistrationId(registrationId);

        return ResponseEntity.ok(requirements);
    }

    /**
     * Endpoint for a Technical Responsible to suggest corrections for a specific
     * document.
     * The document ID, responsible's registration ID, and justification are passed
     * in the request body.
     *
     * @param dto    The DTO containing document ID, registration ID, and
     *               justification.
     * @param result BindingResult for validation errors.
     * @return ResponseEntity with the updated document details or validation
     *         errors.
     * @author Caio Alves
     */
    @Operation(summary = "Suggest Correction for a Document", description = "Allows the designated Technical Responsible to suggest corrections for a specific document associated with one of their requirements.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correction suggestion registered successfully", content = @Content(schema = @Schema(implementation = DocumentDetailResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request (e.g., document not PENDING, missing justification)"),
            @ApiResponse(responseCode = "403", description = "Forbidden - User is not the designated responsible for this document"),
            @ApiResponse(responseCode = "404", description = "Document or Requirement not found")
    })
    @PostMapping("/documents/suggest-correction")
    public ResponseEntity<?> suggestDocumentCorrection(
            @RequestBody @Valid DocumentCorrectionSuggestionDTO dto,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));
        }
        DocumentDetailResponseDTO updatedDocument = documentService.suggestCorrection(dto);
        return ResponseEntity.ok(updatedDocument);
    }

    /**
     * Endpoint for a Technical Responsible (RT) to approve a document (Analysis 1).
     *
     * This endpoint allows a Technical Responsible to approve a document, changing
     * its
     * status from PENDING to APPROVED. Validation errors are returned with status
     * 422.
     *
     * @param dto    DTO containing documentId for the document to approve.
     * @param result BindingResult containing validation results for the request
     *               body.
     * @return ResponseEntity containing the updated document details or validation
     *         errors.
     * 
     * Author: Andesson Reis
     */
    @Operation(summary = "Approve a Document by Technical Responsible", description = "Allows a designated Technical Responsible to approve a document, changing its status from PENDING to APPROVED.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Document successfully approved."),
            @ApiResponse(responseCode = "400", description = "Invalid request or document not in PENDING status."),
            @ApiResponse(responseCode = "404", description = "Document not found."),
            @ApiResponse(responseCode = "422", description = "Validation error in DTO.")
    })
    @PostMapping("/documents/approve-review")
    public ResponseEntity<?> approveDocumentByTechnicalResponsible(
            @RequestBody @Valid TechnicalResponsibleApproveDocumentRequestDTO_TEMP dto,
            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }

        DocumentDetailResponseDTO updatedDocument = documentService.approveDocumentByTechnicalResponsible(dto);
        return ResponseEntity.ok(updatedDocument);
    }

    /**
     * Endpoint for a Technical Responsible (RT) to reject a document (Analysis 1).
     *
     * This endpoint allows a Technical Responsible to reject a document, changing
     * its
     * status to REJECTED. Validation errors are returned with status 422.
     *
     * @param dto    DTO containing documentId and justification for rejection.
     * @param result BindingResult containing validation results for the request
     *               body.
     * @return ResponseEntity containing the updated document details or validation
     *         errors.
     *
     * Author: Andesson Reis
     */
    @Operation(summary = "Reject a Document by Technical Responsible", description = "Allows a designated Technical Responsible to reject a document, changing its status to REJECTED. A justification is required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Document successfully rejected."),
            @ApiResponse(responseCode = "400", description = "Invalid request or document not in PENDING status."),
            @ApiResponse(responseCode = "404", description = "Document not found."),
            @ApiResponse(responseCode = "422", description = "Validation error in DTO (e.g., missing justification).")
    })
    @PostMapping("/documents/reject-review")
    public ResponseEntity<?> rejectDocumentByTechnicalResponsible(
            @RequestBody @Valid TechnicalResponsibleRejectDocumentRequestDTO_TEMP dto,
            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }

        DocumentDetailResponseDTO updatedDocument = documentService.rejectDocumentByTechnicalResponsible(dto);
        return ResponseEntity.ok(updatedDocument);
    }

}
