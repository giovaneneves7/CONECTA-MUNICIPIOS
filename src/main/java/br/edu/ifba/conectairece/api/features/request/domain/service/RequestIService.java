package br.edu.ifba.conectairece.api.features.request.domain.service;

import java.util.List;
import java.util.UUID;

import br.edu.ifba.conectairece.api.features.request.domain.dto.reposnse.RequestResponseDto;
import br.edu.ifba.conectairece.api.features.request.domain.dto.request.RequestPostRequestDto;
import br.edu.ifba.conectairece.api.features.request.domain.model.Request;

/**
 * Defines operations for managing {@link Request} entities.
 * Provides methods for saving, updating, retrieving, and deleting requests.
 *
 * @author Caio
 */

public interface RequestIService {

    RequestResponseDto save(RequestPostRequestDto dto);

    RequestResponseDto update(UUID id, RequestPostRequestDto dto);

    List<RequestResponseDto> findAll();

    Request findById(UUID id);

    void delete(UUID id);
}
