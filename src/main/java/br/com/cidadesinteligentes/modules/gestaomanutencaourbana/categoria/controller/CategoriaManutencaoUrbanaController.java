package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.controller;

import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.request.CategoriaRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.response.CategoriaResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.model.CategoriaManutencaoUrbana;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.service.ICategoriaManutencaoUrbanaService;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.service.ICategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for managing urban maintenance categories.
 * <p>
 * Exposes endpoints for creating, reading, updating, and deleting categories.
 * Delegates business logic to the domain service layer.
 * </p>
 *
 * @author Jeovani
 * @since 1.0
 * @see ICategoriaManutencaoUrbanaService
 * @see CategoriaResponseDTO
 */
@RestController
@RequestMapping("/api/v1/manutencao-urbana/categorias")
@Tag(name = "Gestão de Categorias", description = "Endpoints para gerenciamento das categorias de manutenção urbana")
@RequiredArgsConstructor
public class CategoriaManutencaoUrbanaController {

    private final ICategoriaManutencaoUrbanaService categoriaService;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Creates a new {@link CategoriaManutencaoUrbana}.
     *
     * @param categoriaDto The DTO containing the category data to persist.
     * @param result       Validation result holder for request body errors.
     * @return A {@link ResponseEntity} with status 201 Created or 422 Unprocessable Entity.
     */
    @Operation(summary = "Create a New Category",
            description = "Creates a new urban maintenance category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category successfully created."),
            @ApiResponse(responseCode = "422", description = "Validation error: One or more fields in the request body are invalid.")
    })
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createCategoria(
            @RequestBody @Valid CategoriaRequestDTO categoriaDto,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }

        CategoriaManutencaoUrbana newCategoria = objectMapperUtil.map(categoriaDto, CategoriaManutencaoUrbana.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaService.createCategoria(newCategoria));
    }

    /**
     * Retrieves all {@link CategoriaManutencaoUrbana} entries.
     *
     * @return A list of categories.
     */
    @Operation(summary = "List All Categories",
            description = "Retrieves a list of all maintenance categories stored in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category list retrieved successfully.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoriaResponseDTO.class))))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoriaResponseDTO>> findAllCategorias() {
        return ResponseEntity.ok(categoriaService.findAllCategorias());
    }

    /**
     * Retrieves a {@link CategoriaManutencaoUrbana} by its ID.
     *
     * @param id The ID of the category to retrieve.
     * @return The category details.
     */
    @Operation(summary = "Find Category by ID",
            description = "Retrieves detailed information for a specific category identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found successfully.",
                    content = @Content(schema = @Schema(implementation = CategoriaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Category not found with the given ID.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> findCategoriaById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.findCategoriaById(id));
    }

    /**
     * Updates an existing {@link CategoriaManutencaoUrbana}.
     *
     * @param id           The ID of the category to update.
     * @param categoriaDto The DTO containing updated data.
     * @param result       Validation result holder.
     * @return The updated category.
     */
    @Operation(summary = "Update Category",
            description = "Updates the properties of a category identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "422", description = "Validation error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoria(
            @PathVariable Long id,
            @RequestBody @Valid CategoriaRequestDTO categoriaDto,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }

        CategoriaManutencaoUrbana categoriaUpdates = objectMapperUtil.map(categoriaDto, CategoriaManutencaoUrbana.class);
        return ResponseEntity.ok(this.categoriaService.updateCategoria(id, categoriaUpdates));
    }

    /**
     * Deletes a {@link CategoriaManutencaoUrbana} by its ID.
     *
     * @param id The ID of the category to delete.
     * @return 204 No Content.
     */
    @Operation(summary = "Delete Category",
            description = "Removes a category from the system identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        categoriaService.deleteCategoria(id);
        return ResponseEntity.noContent().build();
    }
}