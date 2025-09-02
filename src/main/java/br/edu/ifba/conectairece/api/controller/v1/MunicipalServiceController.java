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

import br.edu.ifba.conectairece.api.features.municipalservice.domain.dto.request.MunicipalServiceRequestDto;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.dto.response.MunicipalServiceResponseDto;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.model.MunicipalService;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.service.MunicipalServiceIService;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.service.MunicipalServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller responsible for handling MunicipalService endpoints.
 * Provides operations to create, list, retrieve, and delete municipal services.
 *
 * @author Caio Alves
 */

@RestController
@RequestMapping("/api/v1/municipal-services")
@RequiredArgsConstructor
public class MunicipalServiceController {

    private final MunicipalServiceIService municipalServiceService;

    /**
     * Endpoint to create a new municipal service.
     *
     * @param dto DTO containing municipal service data.
     * @return Response with created municipal service data.
     */

    @PostMapping
    public ResponseEntity<MunicipalServiceResponseDto> create(@Valid @RequestBody MunicipalServiceRequestDto dto){
        return ResponseEntity.ok(municipalServiceService.save(dto));
    }

    /**
     * Endpoint to list all municipal services.
     *
     * @return List of all registered municipal services.
     */

    @GetMapping
    public ResponseEntity<List<MunicipalServiceResponseDto>> getAll() {
        return ResponseEntity.ok(municipalServiceService.findAll());
    }

     /**
     * Endpoint to delete a municipal service by its ID.
     *
     * @param id Municipal service ID.
     * @return No content if deletion is successful.
     */

    @GetMapping("/{id}")
    public ResponseEntity<MunicipalService> getById(@Valid @PathVariable Integer id) {
        return ResponseEntity.ok(municipalServiceService.findById(id));
                
    }

    /**
     * Endpoint to delete a municipal service by its ID.
     *
     * @param id Municipal service ID.
     * @return No content if deletion is successful.
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable Integer id) {
        municipalServiceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
