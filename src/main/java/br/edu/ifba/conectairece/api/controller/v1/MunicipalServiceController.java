package br.edu.ifba.conectairece.api.controller.v1;

import java.util.List;

import br.edu.ifba.conectairece.api.features.flow.domain.service.IFlowService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.conectairece.api.features.municipalservice.domain.dto.request.MunicipalServiceRequestDTO;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.dto.response.MunicipalServiceResponseDTO;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.service.IMunicipalServiceService;
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
 * Controller responsible for handling MunicipalService endpoints.
 * Provides operations to create, list, retrieve, and delete municipal services.
 *
 * @author Caio Alves, Jorge Roberto
 */

@RestController
@RequestMapping("/api/v1/municipal-services")
@RequiredArgsConstructor
public class MunicipalServiceController {

    private final IMunicipalServiceService municipalServiceService;
    private final IFlowService flowService;

    /**
     * Endpoint to create a new municipal service.
     *
     * @param dto DTO containing municipal service data.
     * @return Response with created municipal service data.
     */
    @Operation(summary = "Create a new Municipal Service",
            description = "Creates and persists a new municipal service in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Municipal service successfully created",
                    content = @Content(schema = @Schema(implementation = MunicipalServiceResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid")
    })
    @PostMapping(path = "/municipal-service", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody MunicipalServiceRequestDTO dto, BindingResult result){
    
        return result.hasErrors()
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result))
                : ResponseEntity.ok(municipalServiceService.save(dto));

    }

    /**
     * Endpoint to list all municipal services.
     *
     * @return List of all registered municipal services.
     */
    @Operation(summary = "List all Municipal Services",
            description = "Retrieves a list of all registered municipal services.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of municipal services retrieved",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MunicipalServiceResponseDTO.class))))
    })
    @GetMapping
    public ResponseEntity<List<MunicipalServiceResponseDTO>> getAll(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(municipalServiceService.findAll(pageable));
    }

     /**
     * Endpoint to delete a municipal service by its ID.
     *
     * @param id Municipal service ID.
     * @return No content if deletion is successful.
     */
    @Operation(summary = "Retrieve a Municipal Service by ID",
            description = "Fetches details of a municipal service by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Municipal service found",
                    content = @Content(schema = @Schema(implementation = MunicipalServiceResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Municipal service not found")
    })
    @GetMapping(path = "/municipal-service/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MunicipalServiceResponseDTO> getById(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(municipalServiceService.findById(id));
                
    }

    /**
     * Endpoint to delete a municipal service by its ID.
     *
     * @param id Municipal service ID.
     * @return No content if deletion is successful.
     */
    @Operation(summary = "Delete a Municipal Service by ID",
            description = "Deletes a municipal service by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Municipal service successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Municipal service not found")
    })
    @DeleteMapping(path = "/municipal-service/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id) {
        municipalServiceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a flow by the municipal service id passed as a parameter",
               description = "Get a municipal service flow by the id passed as a parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Municipal Service flow found"),
            @ApiResponse(responseCode = "404", description = "Municipal Service flow not found")
    })
    @GetMapping(path = "/municipal-service/{id}/flows/flow", produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFlowByMunicipalServiceId(@PathVariable("id") Long id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.flowService.getFlowByMunicipalServiceId(id));

    }

}
