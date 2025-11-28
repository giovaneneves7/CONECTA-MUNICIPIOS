package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.controller;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.dto.request.RejectionRequestDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.service.ConstructionLicenseRequirementService;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.dto.response.DocumentResponseDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.dto.response.DocumentWithStatusResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.request.RequestPostRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.request.RequestUpdateRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.response.RequestResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.response.RequestResponseWithDetailsDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.response.RequestSimpleResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.service.IRequestService;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.dto.response.UpdateResponseDTO;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

/**
 * Controller responsible for handling Request endpoints.
 * Provides operations to create, list, retrieve, update, and delete requests.
 *
 * @author Caio Alves, Andesson Reis
 */

@RestController
@RequestMapping("/api/v1/requests")
@RequiredArgsConstructor
public class RequestController {

  private final ObjectMapperUtil objectMapperUtil;
  private final IRequestService requestService;
  private final ConstructionLicenseRequirementService constructionLicenseRequirementService;

  /**
   * Endpoint to create a new request.
   *
   * @param dto DTO containing request data.
   * @return Response with created request data.
   */
  @Operation(summary = "Create a new Request", description = "Creates and persists a new request in the system.")
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Request successfully created",
        content = @Content(schema = @Schema(implementation = RequestResponseDTO.class))
      ),
      @ApiResponse(responseCode = "400", description = "Invalid request body"),
      @ApiResponse(responseCode = "422", description = "One or some fields are invalid"),
    }
  )
  @PostMapping("/request")
  public ResponseEntity<?> create(@RequestBody RequestPostRequestDTO dto, BindingResult result) {
    return result.hasErrors()
      ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result))
      : ResponseEntity.ok(requestService.save(dto));
  }

  /**
   * Endpoint to list all requests.
   *
   * @return List of all registered requests.
   */
  @Operation(summary = "List all Requests", description = "Retrieves a list of all registered requests.")
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "List of requests retrieved",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = RequestResponseDTO.class)))
      ),
    }
  )
  @GetMapping
  public ResponseEntity<List<RequestResponseDTO>> getAll(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    return ResponseEntity.ok(requestService.findAll(pageable));
  }

  /**
   * Endpoint to retrieve a request by its ID.
   *
   * @param id Request UUID.
   * @return Request data if found, otherwise 404.
   */
  @Operation(summary = "Retrieve a Request by ID", description = "Fetches details of a request by its UUID.")
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Request found",
        content = @Content(schema = @Schema(implementation = RequestResponseDTO.class))
      ),
      @ApiResponse(responseCode = "404", description = "Request not found"),
    }
  )
  @GetMapping("/request/{id}")
  public ResponseEntity<RequestResponseDTO> getById(@Valid @PathVariable UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(requestService.findById(id));
  }

  /**
   * Endpoint to update an existing request by its ID.
   *
   * @param id  Request UUID.
   * @param dto DTO containing updated request data.
   * @return Updated request data.
   */
  @Operation(
    summary = "Update an existing Request",
    description = "Updates a request by replacing its data with the provided payload."
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Request successfully updated",
        content = @Content(schema = @Schema(implementation = RequestResponseDTO.class))
      ),
      @ApiResponse(responseCode = "404", description = "Request not found"),
      @ApiResponse(responseCode = "422", description = "One or some fields are invalid"),
    }
  )
  @PutMapping("request/{id}")
  public ResponseEntity<?> update(
    @Valid @PathVariable UUID id,
    @RequestBody RequestUpdateRequestDTO dto,
    BindingResult result
  ) {
    return result.hasErrors()
      ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result))
      : ResponseEntity.ok(requestService.update(id, dto));
  }

  /**
   * Endpoint to delete a request by its ID.
   *
   * @param id Request UUID.
   * @return No content if deletion is successful.
   */
  @Operation(summary = "Delete a Request", description = "Deletes a request by its UUID.")
  @ApiResponses(
    value = {
      @ApiResponse(responseCode = "204", description = "Request successfully deleted"),
      @ApiResponse(responseCode = "404", description = "Request not found"),
    }
  )
  @DeleteMapping("/request/{id}")
  public ResponseEntity<RequestSimpleResponseDTO> delete(@Valid @PathVariable UUID id) {
    requestService.delete(id);
    return ResponseEntity.ok(new RequestSimpleResponseDTO(id));
  }

  /**
   * Endpoint to get the list of monitorings linked to the request id passed as a parameter
   *
   * @param id  the Request UUID.
   * @return A list with monitorings data.
   */
  @Operation(
    summary = "Get the list of monitorings linked to the request id",
    description = "Get the list of monitorings linked to the request id passed as a parameter"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Monitoring list found",
        content = @Content(schema = @Schema(implementation = RequestResponseDTO.class))
      ),
      @ApiResponse(responseCode = "404", description = "Request not found"),
      @ApiResponse(responseCode = "422", description = "One or some fields are invalid"),
    }
  )
  @GetMapping("/request/{id}/monitorings")
  public ResponseEntity<?> getMonitorings(
    @Valid @PathVariable UUID id,
    @PageableDefault(size = 20, sort = "name", direction = Sort.Direction.DESC) Pageable pageable
  ) {
    return ResponseEntity.status(HttpStatus.OK).body(this.requestService.getMonitoringListByRequestId(id, pageable));
  }

  /**
   * Endpoint to get the list of updates linked to the request id passed as a parameter
   *
   * @param id  the Request UUID.
   * @return A list with updates data.
   */
  @Operation(
    summary = "Get the list of updates linked to the request id",
    description = "Get the list of updates linked to the request id passed as a parameter"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Update list found",
        content = @Content(schema = @Schema(implementation = UpdateResponseDTO.class))
      ),
      @ApiResponse(responseCode = "404", description = "Request not found"),
      @ApiResponse(responseCode = "422", description = "One or some fields are invalid"),
    }
  )
  @GetMapping("/request/{id}/updates")
  public ResponseEntity<?> getUpdates(
    @Valid @PathVariable UUID id,
    @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
  ) {
    return ResponseEntity.status(HttpStatus.OK).body(this.requestService.getUpdateListByRequestId(id, pageable));
  }

  /**
   * Endpoint to approve a request.
   * @param id The ID of the request.
   * @return The request with its new status.
   */
  @Operation(summary = "Approve a Request", description = "Sets the status of a request to Approved.")
  @ApiResponses(
    value = {
      @ApiResponse(responseCode = "200", description = "Request approved successfully"),
      @ApiResponse(responseCode = "404", description = "Request not found"),
      @ApiResponse(responseCode = "400", description = "Request was already processed"),
    }
  )
  @PostMapping("request/{id}/review/accept")
  public ResponseEntity<?> acceptRequest(@PathVariable Long id) {
    var response = constructionLicenseRequirementService.acceptRequest(id);
    return ResponseEntity.ok(response);
  }

  /**
   * Endpoint to reject a request.
   * @param id The ID of the request.
   * @param dto DTO containing the rejection justification.
   * @return The request with its new status.
   */
  @Operation(summary = "Reject a Request", description = "Sets the status of a request to Rejected.")
  @ApiResponses(
    value = {
      @ApiResponse(responseCode = "200", description = "Request rejected successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid request body or missing justification"),
      @ApiResponse(responseCode = "404", description = "Request not found"),
    }
  )
  @PostMapping("request/{id}/review/reject")
  public ResponseEntity<?> rejectRequest(@PathVariable Long id, @RequestBody @Valid RejectionRequestDTO dto) {
    var response = constructionLicenseRequirementService.rejectRequest(id, dto);
    return ResponseEntity.ok(response);
  }

  /**
   * Endpoint to retrieve a paginated list of requests filtered by type OR status.
   * Both filters (type and status) are optional and provided as query parameters.
   *
   * @param type The request type to filter by (optional).
   * @param status The status (newStatus) to filter requests by (optional).
   * @param pageable Pagination and sorting information.
   * @return A Page of RequestResponseDto (Status 200 OK).
   * @author Jorge Roberto / Caio Alves
   */
  @Operation(
    summary = "List Requests by Filter (Type or Status)",
    description = "Retrieves a paginated list of service requests filtered by a specific type or by the current status recorded in the Status History. Filters are optional."
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Paginated list of requests retrieved successfully",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = RequestResponseDTO.class)))
      ),
      @ApiResponse(responseCode = "404", description = "No requests found matching the criteria."),
    }
  )
  @GetMapping(path = "/", params = "status")
  public ResponseEntity<?> getRequestsByFilter(
    @RequestParam(required = false, name = "status") @Schema(
      description = "The status (newStatus) to filter requests by."
    ) String status,
    @RequestParam(required = false) String type,
    @ParameterObject @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
  ) {
    if (status != null && !status.isEmpty()) {
      Page<RequestResponseDTO> requests = requestService.findAllByStatusHistory_NewStatus(status, pageable);
      return ResponseEntity.ok(requests);
    }

    if (type != null && !type.isEmpty()) {
      Page<RequestResponseDTO> requests = requestService.findByType(type, pageable);
      return ResponseEntity.ok(requests);
    }

    return ResponseEntity.ok(Page.empty());
  }

  /**
   * Endpoint to retrieve a paginated list of requests that have been completed or rejected.
   *
   * @param pageable Pagination and sorting information.
   * @return A Page of RequestResponseWithDetailsDTO (Status 200 OK).
   */
  @Operation(
    summary = "List Finalized Requests (Completed or Rejected)",
    description = "Retrieves a paginated list of requests whose Status History indicates a final status (COMPLETE or REJECTED)."
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Paginated list of finalized requests retrieved successfully",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = RequestResponseDTO.class)))
      ),
      @ApiResponse(responseCode = "404", description = "No finalized requests found."),
    }
  )
  @GetMapping("/finalized")
  public ResponseEntity<Page<?>> getFinalizedRequests(
    @ParameterObject @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
  ) {
    Page<RequestResponseWithDetailsDTO> requests = requestService.findAllFinalizedRequests(pageable);
    return ResponseEntity.ok(requests);
  }

  /**
   * Endpoint to return all Approved Documents associated with a request.
   *
   * @param requestId The ID of the request.
   * @return A list of DocumentResponseDTO (Status 200 OK).
   */
  @Operation(
    summary = "List Documents Approved by Request ID",
    description = "Retrieves a list of approved documents associated with a specific request."
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "List of approved documents retrieved successfully",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentResponseDTO.class)))
      ),
      @ApiResponse(responseCode = "404", description = "No approved documents found for the request."),
    }
  )
  @GetMapping(value = "/request/{id}/approved-documents", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<DocumentWithStatusResponseDTO>> getApprovedDocuments(@PathVariable UUID id) {
    List<DocumentWithStatusResponseDTO> documents = requestService.findApprovedDocumentsByRequestId(id);
    return ResponseEntity.ok(documents);
  }

/**
 * Endpoint to return ALL DOCUMENTS associated with a request.
 * @author Andesson Reis
 * 
 */

 @Operation(
    summary = "List ALL Documents by Request ID",
    description = "Returns a list of ALL documents, regardless of status, associated with a given 'requestID'."
)
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Documents found."),
    @ApiResponse(responseCode = "404", description = "Request or Requirement not found.")
})
@GetMapping(value = "/request/{id}/all-documents", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<List<DocumentWithStatusResponseDTO>> getAllDocumentsByRequestId(
        @PathVariable UUID id
) {
    List<DocumentWithStatusResponseDTO> documents = requestService.findAllDocumentsByRequestId(id);
    return ResponseEntity.ok(documents);
}

}
