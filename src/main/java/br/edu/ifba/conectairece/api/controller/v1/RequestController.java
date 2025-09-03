package br.edu.ifba.conectairece.api.controller.v1;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.conectairece.api.features.request.domain.dto.reposnse.RequestResponseDto;
import br.edu.ifba.conectairece.api.features.request.domain.dto.request.RequestPostRequestDto;
import br.edu.ifba.conectairece.api.features.request.domain.model.Request;
import br.edu.ifba.conectairece.api.features.request.domain.service.RequestIService;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller responsible for handling Request endpoints.
 * Provides operations to create, list, retrieve, update, and delete requests.
 *
 * @author Caio Alves
 */

@RestController
@RequestMapping("/api/v1/requests")
@RequiredArgsConstructor
public class RequestController {

    private final ObjectMapperUtil objectMapperUtil;

    private final RequestIService requestService;


     /**
     * Endpoint to create a new request.
     *
     * @param dto DTO containing request data.
     * @return Response with created request data.
     */

    @PostMapping("/request")
    public ResponseEntity<RequestResponseDto> create(@RequestBody RequestPostRequestDto dto){
        return ResponseEntity.ok(requestService.save(dto));
    }

     /**
     * Endpoint to list all requests.
     *
     * @return List of all registered requests.
     */

    @GetMapping
    public ResponseEntity<List<RequestResponseDto>> getAll(){
        return ResponseEntity.ok(requestService.findAll());
    }

    /**
     * Endpoint to retrieve a request by its ID.
     *
     * @param id Request UUID.
     * @return Request data if found, otherwise 404.
     */
    @GetMapping("request/{id}")
    public ResponseEntity<RequestResponseDto> getById(@Valid @PathVariable UUID id) {
        Request request = requestService.findById(id);
        return ResponseEntity.ok(objectMapperUtil.map(request, RequestResponseDto.class));

    }

    /**
     * Endpoint to update an existing request by its ID.
     *
     * @param id  Request UUID.
     * @param dto DTO containing updated request data.
     * @return Updated request data.
     */

<<<<<<< HEAD
    @PutMapping("request/{id}")
    public ResponseEntity<RequestResponseDto> update(@PathVariable UUID id,
                                                     @RequestBody RequestPostRequestDto dto) {
=======
    @PutMapping("/{id}")
    public ResponseEntity<RequestResponseDto> update(@Valid @PathVariable UUID id,
                                                     @RequestBody RequestPostRequestDto dto ) {
>>>>>>> patterns
        return ResponseEntity.ok(requestService.update(id, dto));
    }

    /**
     * Endpoint to delete a request by its ID.
     *
     * @param id Request UUID.
     * @return No content if deletion is successful.
     */


<<<<<<< HEAD
    @DeleteMapping("request/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
=======
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable UUID id) {
>>>>>>> patterns
        requestService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
