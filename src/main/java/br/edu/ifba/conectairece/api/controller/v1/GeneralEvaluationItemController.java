package br.edu.ifba.conectairece.api.controller.v1;

import br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.dto.request.GeneralEvaluationItemRequestDTO;
import br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.dto.request.GeneralEvaluationItemUpdateRequestDTO;
import br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.dto.response.GeneralEvaluationItemResponseDTO;
import br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.model.GeneralEvaluationItem;
import br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.service.IGeneralEvaluationItemService;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import br.edu.ifba.conectairece.api.infraestructure.util.ResultError;
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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST Controller responsible for managing General Evaluation Item ({@link GeneralEvaluationItem}) resources.
 */
@RestController
@RequestMapping("/api/v1/general-evaluation-items")
@RequiredArgsConstructor
public class GeneralEvaluationItemController {
    private final IGeneralEvaluationItemService generalEvaluationItemService;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Creates and persists a new General Evaluation Item associated with an existing Request.
     *
     * @param dto The DTO containing the item data and the ID of the target request.
     * @param result Validation result of the DTO.
     * @return ResponseEntity with the created item's DTO (Status 200 OK).
     */
    @Operation(summary = "Create General Evaluation Item",
            description = "Creates a new evaluation item and associates it with an existing Request (One-to-Many relationship).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item successfully created",
                    content = @Content(schema = @Schema(implementation = GeneralEvaluationItemResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Target Request not found."),
            @ApiResponse(responseCode = "422", description = "Invalid fields provided",
                    content = @Content(schema = @Schema(implementation = ResultError.class)))
    })
    @PostMapping(value = "/general-evaluation-item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(
        @Valid @RequestBody GeneralEvaluationItemRequestDTO dto,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));
        }
        GeneralEvaluationItemResponseDTO response = this.generalEvaluationItemService.save(
                objectMapperUtil.map(dto, GeneralEvaluationItem.class),
                dto.requestId()
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all General Evaluation Items associated with a specific Request ID.
     *
     * @param requestId The ID of the Request whose evaluation items should be fetched.
     * @param pageable Pagination and sorting parameters (defaults to 'id' ascending).
     * @return ResponseEntity containing the paginated list of items (Status 200 OK).
     */
    @Operation(summary = "List Evaluation Items by Request ID",
            description = "Returns a paginated list of all evaluation items linked to the provided Request ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of items successfully retrieved",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = GeneralEvaluationItemResponseDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Request not found.")
    })
    @GetMapping(path = "/general-evaluation-item/request/{requestId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllByRequestId(
            @PathVariable("requestId") UUID requestId,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.generalEvaluationItemService.findAllByRequestId(requestId, pageable));
    }

    /**
     * Updates an existing General Evaluation Item.
     *
     * @param id The ID of the evaluation item to be updated.
     * @param dto The DTO containing the new data (note and status).
     * @param result Validation result of the DTO.
     * @return Empty ResponseEntity (Status 204 No Content) upon successful update.
     */
    @Operation(summary = "Update Evaluation Item",
            description = "Updates the note and status of an existing General Evaluation Item by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item successfully updated"),
            @ApiResponse(responseCode = "404", description = "General Evaluation Item not found."),
            @ApiResponse(responseCode = "422", description = "Invalid fields provided",
                    content = @Content(schema = @Schema(implementation = ResultError.class)))
    })
    @PutMapping(value = "/general-evaluation-item/{generalEvaluationItemId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(
            @PathVariable("generalEvaluationItemId") Long id,
            @Valid @RequestBody GeneralEvaluationItemUpdateRequestDTO dto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));
        }

        this.generalEvaluationItemService.update(objectMapperUtil.map(dto, GeneralEvaluationItem.class), id);
        return ResponseEntity.noContent().build();
    }
}
