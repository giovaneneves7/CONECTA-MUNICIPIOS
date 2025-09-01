package br.edu.ifba.conectairece.api.controller.v1;

import br.edu.ifba.conectairece.api.features.function.domain.dto.request.FunctionRequestDTO;
import br.edu.ifba.conectairece.api.features.function.domain.dto.response.FunctionResponseDTO;
import br.edu.ifba.conectairece.api.features.function.domain.model.Function;
import br.edu.ifba.conectairece.api.features.function.domain.repository.projection.FunctionProjection;
import br.edu.ifba.conectairece.api.features.function.domain.service.FunctionService;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import br.edu.ifba.conectairece.api.infraestructure.util.dto.PageableDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller responsible for managing {@link Function} resources.
 *
 * @author Jorge Roberto
 */
@RestController
@RequestMapping("/api/v1/functions")
@RequiredArgsConstructor
public class FunctionController {
    private final FunctionService functionService;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Creates and persists a new Function in the system.
     *
     * @param body request payload containing the function details.
     * @return the created {@link FunctionResponseDTO}.
     */
    @Operation(summary = "Create a new Function",
            description = "Creates and persists a new function in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Function successfully created",
                    content = @Content(schema = @Schema(implementation = FunctionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid",
                    content = @Content)
    })
    @PostMapping(value = "/function", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FunctionResponseDTO> save(@RequestBody @Valid FunctionRequestDTO body) {
        try {
            body.setId(null);
            Function function = objectMapperUtil.map(body, Function.class);
            FunctionResponseDTO dto = functionService.save(function);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates an existing Function by replacing its data with the provided payload.
     *
     * @param body request payload containing the updated function details.
     * @return HTTP 204 if the update was successful.
     */
    @Operation(summary = "Update an existing Function",
            description = "Updates a function by replacing its data with the provided payload.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Function successfully updated"),
            @ApiResponse(responseCode = "404", description = "Function not found"),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid")
    })
    @PutMapping(value = "/function/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody @Valid FunctionRequestDTO body, @PathVariable("id") Long id) {
        body.setId(id);
        Function function = objectMapperUtil.map(body, Function.class);
        functionService.update(function);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes a Function by its ID.
     *
     * @param id the ID of the function to be deleted.
     * @return HTTP 204 if the deletion was successful.
     */
    @Operation(summary = "Delete a Function",
            description = "Deletes a function by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Function successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Function not found")
    })
    @DeleteMapping(path = "/function/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        functionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves a paginated list of functions with basic information.
     *
     * @param pageable pagination and sorting configuration.
     * @return a paginated list of {@link FunctionProjection} wrapped in {@link PageableDTO}.
     */
    @Operation(summary = "List all Functions with pagination",
            description = "Retrieves a paginated list of functions with basic information (name and description).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paginated list of functions",
                    content = @Content(schema = @Schema(implementation = PageableDTO.class)))
    })
    @GetMapping
    public ResponseEntity<PageableDTO> findAll(
            @PageableDefault(size = 5, sort="name", direction = Sort.Direction.ASC)
            Pageable pageable) {
        Page<FunctionProjection> projection =  functionService.findAllProjectedBy(pageable);
        PageableDTO dto = objectMapperUtil.map(projection, PageableDTO.class);
        return ResponseEntity.ok(dto);
    }
}
