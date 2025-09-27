package br.edu.ifba.conectairece.api.controller.v1;

import java.util.List;
import java.util.UUID;

import br.edu.ifba.conectairece.api.features.update.domain.dto.response.UpdateResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

import br.edu.ifba.conectairece.api.features.request.domain.dto.reposnse.RequestResponseDto;
import br.edu.ifba.conectairece.api.features.request.domain.dto.request.RequestPostRequestDto;
import br.edu.ifba.conectairece.api.features.request.domain.service.RequestIService;
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

/**
 * Controller responsible for handling Request endpoints.
 * Provides operations to create, list, retrieve, update, and delete requests.
 *
 * @author Caio Alves
 */

@RestController
@RequestMapping("/api/v1/requests")
@RequiredArgsConstructor
public class RequestController {

    private final ObjectMapperUtil objectMapperUtil;
    private final RequestIService requestService;


     /**
     * Endpoint to create a new request.
     *
     * @param dto DTO containing request data.
     * @return Response with created request data.
     */
    @Operation(summary = "Create a new Request",
            description = "Creates and persists a new request in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request successfully created",
                    content = @Content(schema = @Schema(implementation = RequestResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid")
    })
    @PostMapping("/request")
    public ResponseEntity<?> create(@RequestBody RequestPostRequestDto dto, BindingResult result){
        return result.hasErrors()
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result))
                : ResponseEntity.ok(requestService.save(dto));
    }

     /**
     * Endpoint to list all requests.
     *
     * @return List of all registered requests.
     */
    @Operation(summary = "List all Requests",
            description = "Retrieves a list of all registered requests.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of requests retrieved",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = RequestResponseDto.class))))
    })
    @GetMapping
    public ResponseEntity<List<RequestResponseDto>> getAll(){
        return ResponseEntity.ok(requestService.findAll());
    }

    /**
     * Endpoint to retrieve a request by its ID.
     *
     * @param id Request UUID.
     * @return Request data if found, otherwise 404.
     */
    @Operation(summary = "Retrieve a Request by ID",
            description = "Fetches details of a request by its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request found",
                    content = @Content(schema = @Schema(implementation = RequestResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Request not found")
    })
    @GetMapping("/request/{id}")
    public ResponseEntity<RequestResponseDto> getById(@Valid @PathVariable UUID id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(requestService.findById(id));

    }

    /**
     * Endpoint to update an existing request by its ID.
     *
     * @param id  Request UUID.
     * @param dto DTO containing updated request data.
     * @return Updated request data.
     */
    @Operation(summary = "Update an existing Request",
            description = "Updates a request by replacing its data with the provided payload.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request successfully updated",
                    content = @Content(schema = @Schema(implementation = RequestResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Request not found"),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid")
    })
    @PutMapping("request/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable UUID id,
                                                     @RequestBody RequestPostRequestDto dto, BindingResult result) {
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
    @Operation(summary = "Delete a Request",
            description = "Deletes a request by its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Request successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Request not found")
    })
    @DeleteMapping("/request/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable UUID id) {
        requestService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint to get the list of monitorings linked to the request id passed as a parameter
     *
     * @param id  the Request UUID.
     * @return A list with monitorings data.
     */
    @Operation(summary = "Get the list of monitorings linked to the request id",
            description = "Get the list of monitorings linked to the request id passed as a parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monitoring list found",
                    content = @Content(schema = @Schema(implementation = RequestResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Request not found"),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid")
    })
    @GetMapping("/request/{id}/monitorings")
    public ResponseEntity<?> getMonitorings(
            @Valid @PathVariable UUID id,
            @PageableDefault(size = 20, sort = "name", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.requestService.getMonitoringListByRequestId(id, pageable));

    }

    /**
     * Endpoint to get the list of updates linked to the request id passed as a parameter
     *
     * @param id  the Request UUID.
     * @return A list with updates data.
     */
    @Operation(summary = "Get the list of updates linked to the request id",
            description = "Get the list of updates linked to the request id passed as a parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update list found",
                    content = @Content(schema = @Schema(implementation = UpdateResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Request not found"),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid")
    })
    @GetMapping("/request/{id}/updates")
    public ResponseEntity<?> getUpdates(
            @Valid @PathVariable UUID id,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.requestService.getUpdateListByRequestId(id, pageable));

    }
}
