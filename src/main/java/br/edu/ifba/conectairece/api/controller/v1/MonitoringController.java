package br.edu.ifba.conectairece.api.controller.v1;

import br.edu.ifba.conectairece.api.features.monitoring.domain.dto.request.MonitoringRequestDTO;
import br.edu.ifba.conectairece.api.features.monitoring.domain.service.IMonitoringService;
import br.edu.ifba.conectairece.api.infraestructure.util.ResultError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;
import java.util.UUID;

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

    @Autowired
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
            @ApiResponse(responseCode = "200", description = "Monitoring successfully created",
                    content = @Content(schema = @Schema(implementation = MonitoringRequestDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid")
    })
    @PostMapping("/monitoring")
    public ResponseEntity<?> save(@RequestBody MonitoringRequestDTO dto, BindingResult result){

        return result.hasErrors()
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result))
                : ResponseEntity.ok(this.monitoringService.save(dto));

    }

}
