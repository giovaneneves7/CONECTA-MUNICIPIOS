package br.edu.ifba.conectairece.api.controller.v1;

import br.edu.ifba.conectairece.api.features.monitoring.domain.dto.request.MonitoringRequestDTO;
import br.edu.ifba.conectairece.api.features.monitoring.domain.dto.request.MonitoringUpdateRequestDTO;
import br.edu.ifba.conectairece.api.features.monitoring.domain.dto.response.MonitoringResponseDTO;
import br.edu.ifba.conectairece.api.features.monitoring.domain.service.IMonitoringService;
import br.edu.ifba.conectairece.api.infraestructure.util.ResultError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  Controller responsible for handling Category endpoints.
 *  Provides operations to create, list, retrieve, and delete categories.
 *
 * @author Giovane Neves
 */

@RestController
@RequestMapping(path = "/api/v1/monitorings/")
@RequiredArgsConstructor
public class MonitoringController {

    private final IMonitoringService monitoringService;

    /**
     * Endpoint to create a new monitoring.
     *
     * @param dto DTO containing monitoring data.
     * @return Response with created monitoring data.
     */
    @Operation(summary = "Create a new Monitoring",
            description = "Creates and persists a new monitoring in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Monitoring successfully created",
                    content = @Content(schema = @Schema(implementation = MonitoringRequestDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid")
    })
    @PostMapping(path = "/monitoring", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody MonitoringRequestDTO dto, BindingResult result){

        return result.hasErrors()
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result))
                : ResponseEntity.status(HttpStatus.CREATED).body(this.monitoringService.save(dto));

    }


    /**
     * Endpoint to update a existing monitoring.
     *
     * @param dto DTO containing monitoring data.
     * @return Response with updated monitoring data.
     */
    @Operation(summary = "Update a existing Monitoring",
            description = "Updates a existing monitoring in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monitoring successfully updated",
                    content = @Content(schema = @Schema(implementation = MonitoringUpdateRequestDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid")
    })
    @PutMapping(path = "/monitoring", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> update(@RequestBody MonitoringUpdateRequestDTO dto, BindingResult result){

        return result.hasErrors()
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result))
                : ResponseEntity.status(HttpStatus.OK).body(this.monitoringService.updateMonitoring(dto));

    }

    @Operation(summary = "List all monitorings with pagination",
            description = "Retrieves a paginated list of monitorings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paginated list of monitorings",
                    content = @Content(schema = @Schema(implementation = MonitoringResponseDTO.class)))
    })
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll(@PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.monitoringService.getAllMonitorings(pageable));

    }

}
