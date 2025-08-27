package br.edu.ifba.conectairece.api.controller.v1;

import java.util.List;

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
import br.edu.ifba.conectairece.api.features.category.domain.service.CategoryService;
import lombok.RequiredArgsConstructor;

/**
 * Controller responsible for handling Category endpoints.
 * Provides operations to create, list, retrieve, and delete categories.
 *
 * @author Caio Alves
 */

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Endpoint to create a new category.
     *
     * @param dto DTO containing category data.
     * @return Response with created category data.
     */

    @PostMapping
    public ResponseEntity<CategoryResponseDto> create(@RequestBody CategoryRequestDto dto) {
        System.out.println(">>> Recebido DTO: " + dto.getName() + " - " + dto.getDescription());
        return ResponseEntity.ok(categoryService.save(dto));
    }

     /**
     * Endpoint to list all categories.
     *
     * @return List of all registered categories.
     */

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

    @GetMapping("/{id}")
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
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
