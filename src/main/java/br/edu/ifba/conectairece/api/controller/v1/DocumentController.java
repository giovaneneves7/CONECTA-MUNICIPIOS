package br.edu.ifba.conectairece.api.controller.v1;

import br.edu.ifba.conectairece.api.features.document.domain.dto.request.DocumentRejectionDTO;
import br.edu.ifba.conectairece.api.features.document.domain.dto.request.DocumentRequestDTO;
import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentDetailResponseDTO;
import br.edu.ifba.conectairece.api.features.document.domain.model.Document;
import br.edu.ifba.conectairece.api.features.document.domain.service.IDocumentService;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import br.edu.ifba.conectairece.api.infraestructure.util.ResultError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller responsible for managing document review operations.
 * <p>
 * Provides endpoints for creating, approving, and rejecting documents submitted
 * by users, supporting administrative workflows for document validation and auditing.
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *   <li>Accept document uploads associated with a specific requirement.</li>
 *   <li>Approve or reject documents under review.</li>
 *   <li>Return standardized API responses with appropriate HTTP status codes.</li>
 * </ul>
 *
 * @author
 *     Andesson Reis
 * @since
 *     1.0
 * @see br.edu.ifba.conectairece.api.features.document.domain.service.IDocumentService
 * @see br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentDetailResponseDTO
 */
@RestController
@RequestMapping("/api/v1/documents")
@Tag(name = "Document Review", description = "Endpoints for reviewing and managing user-submitted documents")
@RequiredArgsConstructor
public class DocumentController {

    private final IDocumentService documentService;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Creates a new {@link Document} associated with an existing requirement.
     * <p>
     * This endpoint receives a {@link DocumentRequestDTO}, validates it, maps it to a {@link Document} entity,
     * and delegates the creation logic to the domain service layer. The created document will typically
     * be initialized with a {@code PENDING} status, awaiting administrative review.
     * </p>
     *
     * @param requirementId The unique numeric identifier (ID) of the requirement to which the document will be associated.
     * @param documentDto   The DTO containing the document data to be persisted.
     * @param result        Validation result holder for request body errors.
     * @return A {@link ResponseEntity} with:
     * <ul>
     *   <li><b>201 Created</b> – When the document is successfully created.</li>
     *   <li><b>404 Not Found</b> – When the specified requirement does not exist.</li>
     *   <li><b>422 Unprocessable Entity</b> – When validation errors occur in the request body.</li>
     * </ul>
     */
    @Operation(
        summary = "Create a New Document for a Requirement",
        description = "Creates a new document and associates it with an existing requirement identified by its numeric ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Document successfully created."),
        @ApiResponse(responseCode = "404", description = "Requirement not found with the provided ID."),
        @ApiResponse(responseCode = "422", description = "Validation error: One or more fields in the request body are invalid.")
    })
    @PostMapping(
        path = "/requirement/{requirementId}/document",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createDocument(
            @PathVariable Long requirementId,
            @RequestBody @Valid DocumentRequestDTO documentDto,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                                 .body(ResultError.getResultErrors(result));
        }

        Document newDocument = objectMapperUtil.map(documentDto, Document.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(this.documentService.createDocument(requirementId, newDocument));
    }

    /**
     * Approves a specific document.
     * <p>
     * This endpoint transitions a document from {@code PENDING} to {@code APPROVED},
     * provided it satisfies the business validation rules.
     * </p>
     *
     * @param documentId The unique identifier (UUID) of the document to approve.
     * @return A {@link ResponseEntity} containing the updated {@link DocumentDetailResponseDTO}.
     */
    @Operation(
        summary = "Approve a Document",
        description = "Transitions a document’s status from PENDING to APPROVED."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Document approved successfully."),
        @ApiResponse(responseCode = "404", description = "Document not found with the given ID."),
        @ApiResponse(responseCode = "400", description = "The document is not in a PENDING state and cannot be approved.")
    })
    @PostMapping("/{documentId}/review/accept")
    public ResponseEntity<DocumentDetailResponseDTO> approveDocument(@PathVariable UUID documentId) {
        DocumentDetailResponseDTO updatedDocument = documentService.approveDocument(documentId);
        return ResponseEntity.ok(updatedDocument);
    }

    /**
     * Rejects a specific document.
     * <p>
     * This endpoint transitions a document from {@code PENDING} to {@code REJECTED},
     * requiring a mandatory justification to be provided in the request body.
     * </p>
     *
     * @param documentId   The unique identifier (UUID) of the document to reject.
     * @param rejectionDto The DTO containing the justification for rejection.
     * @return A {@link ResponseEntity} containing the updated {@link DocumentDetailResponseDTO}.
     */
    @Operation(
        summary = "Reject a Document",
        description = "Transitions a document’s status to REJECTED and records a justification."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Document rejected successfully."),
        @ApiResponse(responseCode = "400", description = "Invalid request or document not in a PENDING state."),
        @ApiResponse(responseCode = "404", description = "Document not found with the given ID.")
    })
    @PostMapping("/{documentId}/review/reject")
    public ResponseEntity<DocumentDetailResponseDTO> rejectDocument(
            @PathVariable UUID documentId,
            @RequestBody @Valid DocumentRejectionDTO rejectionDto) {
        DocumentDetailResponseDTO updatedDocument = documentService.rejectDocument(documentId, rejectionDto);
        return ResponseEntity.ok(updatedDocument);
    }
}
