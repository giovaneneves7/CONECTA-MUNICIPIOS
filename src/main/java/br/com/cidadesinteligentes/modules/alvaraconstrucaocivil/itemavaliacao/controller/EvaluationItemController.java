package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.controller;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.dto.request.EvaluationItemRequestDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.dto.request.EvaluationItemUpdateRequestDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.dto.response.EvaluationItemResponseDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.model.EvaluationItem;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.service.IEvaluationItemService;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * REST Controller responsible for managing Evaluation Item ({@link EvaluationItem}) resources.
 * <p>
 * This controller handles the creation, retrieval, and updating of evaluation criteria
 * associated with a specific document during a review process.
 *
 * @author Jorge Roberto
 */
@RestController
@RequestMapping("/api/v1/evaluation-items")
@RequiredArgsConstructor
public class EvaluationItemController {
    private final IEvaluationItemService evaluationItemService;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Creates and persists a new Evaluation Item, associating it with a specific Document ID.
     *
     * @param dto The DTO containing the item details (note, status) and the target Document ID.
     * @param result Validation result of the DTO.
     * @return ResponseEntity with the created item's DTO (Status 200 OK).
     */
    @Operation(summary = "Create Evaluation Item",
            description = "Creates a new evaluation item and associates it with an existing Document ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item successfully created",
                    content = @Content(schema = @Schema(implementation = EvaluationItemResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Target Document not found."),
            @ApiResponse(responseCode = "422", description = "Invalid fields provided",
                    content = @Content(schema = @Schema(implementation = ResultError.class)))
    })
    @PostMapping(value = "/evaluation-item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save (
        @Valid @RequestBody EvaluationItemRequestDTO dto,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));
        }
        EvaluationItemResponseDTO response = this.evaluationItemService.save(
                dto.documentId(),
                objectMapperUtil.map(dto, EvaluationItem.class)
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a paginated list of all Evaluation Items associated with a specific Document ID.
     *
     * @param documentId The ID of the Document to retrieve items for.
     * @param pageable Pagination and sorting parameters (defaults to sorting by 'id' ascending).
     * @return ResponseEntity containing the paginated list of items (Status 200 OK).
     */
    @Operation(summary = "List Items by Document ID",
            description = "Returns a paginated list of all evaluation items associated with the given Document ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of items successfully retrieved",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EvaluationItemResponseDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Document not found.")
    })
    @GetMapping(path = "/evaluation-item/document/{documentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllByDocumentId (
        @PathVariable("documentId") UUID documentId,
        @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.evaluationItemService.findAllByDocumentId(documentId, pageable));
    }

    /**
     * Updates an existing Evaluation Item identified by its ID.
     *
     * @param id The unique identifier of the evaluation item to be updated.
     * @param dto The DTO containing the updated note and status.
     * @param result Validation result of the DTO.
     * @return Empty ResponseEntity (Status 204 No Content) upon successful update.
     */
    @Operation(summary = "Update Evaluation Item",
            description = "Updates the note and status of an existing Evaluation Item identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item successfully updated"),
            @ApiResponse(responseCode = "404", description = "Evaluation Item not found."),
            @ApiResponse(responseCode = "422", description = "Invalid fields provided",
                    content = @Content(schema = @Schema(implementation = ResultError.class)))
    })
    @PutMapping(value = "/evaluation-item/{evaluationItemId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update (
            @PathVariable("evaluationItemId") UUID id,
            @Valid @RequestBody EvaluationItemUpdateRequestDTO dto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));
        }
        this.evaluationItemService.update(
                id,
                objectMapperUtil.map(dto, EvaluationItem.class)
        );
        return ResponseEntity.noContent().build();
    }

}
