package br.edu.ifba.conectairece.api.controller.v1;

import java.util.List;

import br.edu.ifba.conectairece.api.features.category.domain.service.CategoryIService;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.dto.response.MunicipalServiceResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.conectairece.api.features.category.domain.dto.request.CategoryRequestDto;
import br.edu.ifba.conectairece.api.features.category.domain.dto.response.CategoryResponseDto;
import lombok.RequiredArgsConstructor;

/**
 * Controller responsible for handling Category endpoints.
 * Provides operations to create, list, retrieve, and delete categories.
 *
 * @author Caio Alves, Jorge Roberto
 */

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryIService categoryService;

    @Operation(summary = "create new Category",
        description = "Creates and persists a new category in the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category sucessfuly created",
            content = @Content (schema = @Schema(implementation = CategoryResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request body"),
        @ApiResponse(responseCode = "422", description = "One or some fields are invalid")           
    })
    /**
     * Endpoint to create a new category.
     *
     * @param dto DTO containing category data.
     * @return Response with created category data.
     */
    @PostMapping(path ="/category")
    public ResponseEntity<CategoryResponseDto> create(@RequestBody @Valid CategoryRequestDto dto) {
        return ResponseEntity.ok(categoryService.save(dto));
    }
     /**
     * Endpoint to list all categories.
     *
     * @return List of all registered categories.
     */
 
    @Operation(summary = "List all Categories",
            description = "Retrieves a list of all registered categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of categories retrieved",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResponseDto.class))))
    })
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    /**
     * Endpoint to retrieve a category by its ID.
     *
     * @param id Category ID.
     * @return Category data if found, otherwise 404.
     */
    @Operation(summary = "Retrieve a Category by ID",
            description = "Fetches details of a category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(schema = @Schema(implementation = CategoryResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable Integer id) {
        return categoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

     /**
     * Endpoint to delete a category by its ID.
     *
     * @param id Category ID.
     * @return No content if deletion is successful.
     */
    @Operation(summary = "Delete a Category by ID",
        description = "Deletes a category from the system by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Category successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/category/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
