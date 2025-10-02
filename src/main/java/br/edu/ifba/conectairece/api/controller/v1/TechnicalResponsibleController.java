package br.edu.ifba.conectairece.api.controller.v1;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request.TechnicalResponsibleRequestDto;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.response.TechnicalResponsibleResponseDto;
import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.service.ITechnicalResponsibleService;
import br.edu.ifba.conectairece.api.infraestructure.util.ResultError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller responsible for handling TechnicalResponsible endpoints.
 * Provides operations to create, list, retrieve, and delete technical responsibles.
 *
 * @author Caio Alves
 */

@RestController
@RequestMapping("/api/v1/technical-responsibles")
@RequiredArgsConstructor
public class TechnicalResponsibleController {

    private final ITechnicalResponsibleService service;

    @Operation(summary = "Create new Technical Responsible",
            description = "Creates and persists a new technical responsible in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Technical Responsible successfully created",
                    content = @Content(schema = @Schema(implementation = TechnicalResponsibleResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid")
    })

    @PostMapping(path ="users/{userId}/profiles/technical-responsible")
    public ResponseEntity<?> create(@PathVariable UUID userId, @RequestBody @Valid TechnicalResponsibleRequestDto dto, BindingResult result) {

            if (result.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result));
            }

            TechnicalResponsibleResponseDto responseDto = service.save(userId, dto);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "List all Technical Responsibles",
        description = "Retrieves a list of all registered technical responsibles.")
    @GetMapping
    public ResponseEntity<List<TechnicalResponsibleResponseDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Retrieve a Technical Responsible by ID",
        description = "Fetches details of a technical responsible by its ID.")

    @GetMapping("/technical-responsible/{id}")
    public ResponseEntity<TechnicalResponsibleResponseDto> getById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a Technical Responsible by ID",
        description = "Deletes a technical responsible from the system by its ID.")
    @DeleteMapping("/technical-responsible/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
