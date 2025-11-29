package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.controller;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.dto.request.DocumentRejectionDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.dto.request.DocumentRequestDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.dto.request.DocumentUpdateRequestDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.dto.response.DocumentDetailResponseDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.dto.response.DocumentSimpleResponseDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.model.Document;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.service.IDocumentService;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

import java.util.List;
import java.util.UUID;

/**
 * REST controller responsible for managing documents within the system.
 * <p>
 * Exposes endpoints for creating, reading, updating, deleting, and processing documents.
 * Delegates business logic to the domain service layer while handling HTTP requests/responses.
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *   <li>Accept and validate document payloads.</li>
 *   <li>Delegate document-related operations to the service layer.</li>
 *   <li>Return standardized HTTP responses with proper status codes.</li>
 * </ul>
 *
 * @author Andesson Reis
 * @since 1.0
 * @see IDocumentService
 * @see DocumentDetailResponseDTO
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
     *
     * @param requirementId The numeric identifier (Long) of the requirement to which the document will be linked.
     * @param documentDto   The DTO containing the document data to persist.
     * @param result        Validation result holder for request body errors.
     * @return A {@link ResponseEntity} with:
     * <ul>
     *   <li><b>201 Created</b> – When the document is successfully created.</li>
     *   <li><b>404 Not Found</b> – When the specified requirement does not exist.</li>
     *   <li><b>422 Unprocessable Entity</b> – When validation errors occur in the request body.</li>
     * </ul>
     */
    @Operation(summary = "Create a New Document for a Requirement",
               description = "Creates a new document and associates it with an existing requirement identified by its numeric ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Document successfully created."),
        @ApiResponse(responseCode = "404", description = "Requirement not found with the provided ID."),
        @ApiResponse(responseCode = "422", description = "Validation error: One or more fields in the request body are invalid.")
    })
    @PostMapping(
        path = "/requirement/{id}/document",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createDocument(
            @PathVariable Long id,
            @RequestBody @Valid DocumentRequestDTO documentDto,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                                 .body(ResultError.getResultErrors(result));
        }

        Document newDocument = objectMapperUtil.map(documentDto, Document.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(documentService.createDocument(id, newDocument));
    }

    /**
     * Retrieves all {@link Document} entries from the system.
     *
     * @return A {@link ResponseEntity} with:
     * <ul>
     *   <li><b>200 OK</b> – When the documents are successfully retrieved.</li>
     * </ul>
     */
    @Operation(summary = "List All Documents",
               description = "Retrieves a list of all documents stored in the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Document list retrieved successfully.",
                     content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentDetailResponseDTO.class))))
    })
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DocumentDetailResponseDTO>> findAllDocuments() {
        return ResponseEntity.ok(documentService.findAllDocuments());
    }

    /**
     * Retrieves a {@link Document} by its UUID.
     *
     * @param documentId The UUID of the document to retrieve.
     * @return A {@link ResponseEntity} with:
     * <ul>
     *   <li><b>200 OK</b> – When the document is found.</li>
     *   <li><b>404 Not Found</b> – When no document exists with the provided UUID.</li>
     * </ul>
     */
    @Operation(summary = "Find Document by ID",
               description = "Retrieves detailed information for a specific document identified by its UUID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Document found successfully.",
                     content = @Content(schema = @Schema(implementation = DocumentDetailResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Document not found with the given ID.")
    })
    @GetMapping("/document/{id}")
    public ResponseEntity<DocumentDetailResponseDTO> findDocumentById(@PathVariable UUID id) {
        return ResponseEntity.ok(documentService.findDocumentById(id));
    }

    /**
     * Updates an existing {@link Document} identified by its UUID.
     *
     * @param documentId  The UUID of the document to update.
     * @param documentDto The DTO containing updated document data.
     * @param result      Validation result holder for request body errors.
     * @return A {@link ResponseEntity} with:
     * <ul>
     *   <li><b>200 OK</b> – When the document is successfully updated.</li>
     *   <li><b>404 Not Found</b> – When the document with the given UUID does not exist.</li>
     *   <li><b>422 Unprocessable Entity</b> – When validation errors occur.</li>
     * </ul>
     */
    @Operation(summary = "Update Document",
               description = "Updates the properties of a document identified by its UUID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Document updated successfully"),
        @ApiResponse(responseCode = "404", description = "Document not found"),
        @ApiResponse(responseCode = "422", description = "Validation error")
    })
    @PutMapping("/document/{id}")
    public ResponseEntity<?> updateDocument(
            @PathVariable UUID id,
            @RequestBody @Valid DocumentUpdateRequestDTO documentDto,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }

        return ResponseEntity.ok(this.documentService.updateDocument(id, objectMapperUtil.map(documentDto, Document.class)));
    }

    /**
     * Deletes a {@link Document} by its UUID.
     *
     * @param documentId The UUID of the document to delete.
     * @return A {@link ResponseEntity} with:
     * <ul>
     *   <li><b>204 No Content</b> – When the document is successfully deleted.</li>
     *   <li><b>404 Not Found</b> – When no document exists with the provided UUID.</li>
     * </ul>
     */
     @Operation(summary = "Delete Document",
               description = "Removes a document from the system identified by its UUID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Document deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Document not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<DocumentSimpleResponseDTO> deleteDocument(@PathVariable UUID id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok(new DocumentSimpleResponseDTO(id));
    }
    
    /**
     * Approves a {@link Document}, transitioning its status from PENDING to APPROVED.
     *
     * @param documentId The UUID of the document to approve.
     * @return A {@link ResponseEntity} with:
     * <ul>
     *   <li><b>200 OK</b> – When the document is successfully approved.</li>
     *   <li><b>404 Not Found</b> – When no document exists with the provided UUID.</li>
     *   <li><b>400 Bad Request</b> – When the document is not in PENDING status.</li>
     * </ul>
     */
    @Operation(summary = "Approve a Document",
               description = "Transitions a document’s status from PENDING to APPROVED.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Document approved successfully."),
        @ApiResponse(responseCode = "404", description = "Document not found with the given ID."),
        @ApiResponse(responseCode = "400", description = "The document is not in a PENDING state and cannot be approved.")
    })
    @PostMapping("/{id}/review/accept")
    public ResponseEntity<DocumentDetailResponseDTO> approveDocument(@PathVariable UUID id) {
        return ResponseEntity.ok(documentService.approveDocument(id));
    }

    /**
     * Rejects a {@link Document}, transitioning its status from PENDING to REJECTED.
     *
     * @param documentId   The UUID of the document to reject.
     * @param rejectionDto DTO containing rejection justification.
     * @return A {@link ResponseEntity} with:
     * <ul>
     *   <li><b>200 OK</b> – When the document is successfully rejected.</li>
     *   <li><b>404 Not Found</b> – When no document exists with the provided UUID.</li>
     *   <li><b>400 Bad Request</b> – When the document is not in PENDING status or invalid request.</li>
     * </ul>
     */
    @Operation(summary = "Reject a Document",
               description = "Transitions a document’s status from PENDING to REJECTED, requiring a justification.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Document rejected successfully."),
        @ApiResponse(responseCode = "400", description = "Invalid request or document not in a PENDING state."),
        @ApiResponse(responseCode = "404", description = "Document not found with the given ID.")
    })
    @PostMapping("/{id}/review/reject")
    public ResponseEntity<DocumentDetailResponseDTO> rejectDocument(
            @PathVariable UUID id,
            @RequestBody @Valid DocumentRejectionDTO rejectionDto) {

        return ResponseEntity.ok(documentService.rejectDocument(id, rejectionDto));
    }
}
