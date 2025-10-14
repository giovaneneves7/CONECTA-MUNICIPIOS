package br.edu.ifba.conectairece.api.controller.v1;

import br.edu.ifba.conectairece.api.features.document.domain.dto.request.DocumentRejectionDTO;
// 1. Troca do DTO de resposta importado
import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentDetailResponseDTO;
import br.edu.ifba.conectairece.api.features.document.domain.service.IDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for handling document review operations.
 * <p>
 * Exposes endpoints for administrators to approve or reject documents submitted by users.
 * </p>
 *
 * @author Andesson Reis (Refactored)
 */
@RestController
@RequestMapping("/api/v1/documents")
@Tag(name = "Document Review", description = "Endpoints for reviewing individual documents")
@RequiredArgsConstructor
public class DocumentController {

    private final IDocumentService documentService;

    /**
     * Approves a specific document.
     * <p>
     * This endpoint transitions the status of a PENDING document to APPROVED.
     * </p>
     *
     * @param documentId The unique identifier (UUID) of the document to approve.
     * @return A ResponseEntity containing the {@link DocumentDetailResponseDTO} with the updated status.
     */
    @Operation(summary = "Approve a Document", description = "Sets the status of a specific document to APPROVED.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Document approved successfully."),
            @ApiResponse(responseCode = "404", description = "Document not found with the given ID."),
            @ApiResponse(responseCode = "400", description = "The document is not in a PENDING state and cannot be approved.")
    })
    @PostMapping("/{documentId}/review/accept")
    // 2. Tipo de retorno atualizado
    public ResponseEntity<DocumentDetailResponseDTO> approveDocument(@PathVariable UUID documentId) {
        // 3. Tipo da variável atualizado
        DocumentDetailResponseDTO updatedDocument = documentService.approveDocument(documentId);
        return ResponseEntity.ok(updatedDocument);
    }

    /**
     * Rejects a specific document.
     * <p>
     * This endpoint transitions the status of a PENDING document to REJECTED,
     * requiring a mandatory justification in the request body.
     * </p>
     *
     * @param documentId The unique identifier (UUID) of the document to reject.
     * @param rejectionDto A DTO containing the mandatory justification for the rejection.
     * @return A ResponseEntity containing the {@link DocumentDetailResponseDTO} with the updated status and justification note.
     */
    @Operation(summary = "Reject a Document", description = "Sets the status of a specific document to REJECTED and records a justification.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Document rejected successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid request body (e.g., missing justification) or the document is not in a PENDING state."),
            @ApiResponse(responseCode = "404", description = "Document not found with the given ID.")
    })
    @PostMapping("/{documentId}/review/reject")
    // 2. Tipo de retorno atualizado
    public ResponseEntity<DocumentDetailResponseDTO> rejectDocument(
            @PathVariable UUID documentId,
            @RequestBody @Valid DocumentRejectionDTO rejectionDto) {
        // 3. Tipo da variável atualizado
        DocumentDetailResponseDTO updatedDocument = documentService.rejectDocument(documentId, rejectionDto);
        return ResponseEntity.ok(updatedDocument);
    }
}