package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.controller;

import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.request.CategoriaCreateRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.request.CategoriaUpdateRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.response.CategoriaResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.service.ICategoriaManutencaoUrbanaService;
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
 *
 * @author Jeovani
 * @since 1.0
 */
@RestController
// Base path ajustado conforme contexto, os métodos adicionam o sufixo solicitado
@RequestMapping("/api/v1/manutencao-urbana/categorias")
@Tag(name = "Gestão de Categorias", description = "Endpoints para gerenciamento das categorias de manutenção urbana")
@RequiredArgsConstructor
public class CategoriaManutencaoUrbanaController {

    private final ICategoriaManutencaoUrbanaService categoriaService;

    // POST /api/v1/manutencao-urbana/categorias/categoria
    @Operation(summary = "Create a New Category", description = "Creates a new urban maintenance category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category successfully created."),
            @ApiResponse(responseCode = "422", description = "Validation error.")
    })
    @PostMapping(value = "/categoria",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> save(
            @RequestBody @Valid CategoriaCreateRequestDTO categoriaDto,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }

        // Passa o DTO direto para o service
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaService.save(categoriaDto));
    }

    // GET /api/v1/manutencao-urbana/categorias
    @Operation(summary = "List All Categories", description = "Retrieves a list of all maintenance categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List retrieved successfully.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoriaResponseDTO.class))))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoriaResponseDTO>> findAll() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    // GET /api/v1/manutencao-urbana/categorias/categoria/{id}
    @Operation(summary = "Find Category by ID", description = "Retrieves detailed information for a specific category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found successfully."),
            @ApiResponse(responseCode = "404", description = "Category not found.")
    })
    @GetMapping("/categoria/{id}")
    public ResponseEntity<CategoriaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    // PUT /api/v1/manutencao-urbana/categorias/categoria/{id}
    @Operation(summary = "Update Category", description = "Updates the properties of a category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "422", description = "Validation error")
    })
    @PutMapping("/categoria/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody @Valid CategoriaUpdateRequestDTO categoriaDto,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }

        // Nota: O Service espera um DTO que contenha o ID.
        // Idealmente o ID da URL deve bater com o do DTO, ou garantimos aqui.
        if (!id.equals(categoriaDto.id())) {

        }

        return ResponseEntity.ok(categoriaService.update(categoriaDto));
    }

    // DELETE /api/v1/manutencao-urbana/categorias/categoria/{id}
    @Operation(summary = "Delete Category", description = "Removes a category from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/categoria/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.ok(id);
    }
}