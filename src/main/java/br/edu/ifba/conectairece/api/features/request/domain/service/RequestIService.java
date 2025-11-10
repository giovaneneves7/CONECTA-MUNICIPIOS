package br.edu.ifba.conectairece.api.features.request.domain.service;

import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentResponseDTO;
import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentWithStatusResponseDTO;
import br.edu.ifba.conectairece.api.features.monitoring.domain.dto.response.MonitoringResponseDTO;
import br.edu.ifba.conectairece.api.features.request.domain.dto.reposnse.RequestResponseDto;
import br.edu.ifba.conectairece.api.features.request.domain.dto.reposnse.RequestResponseWithDetailsDTO;
import br.edu.ifba.conectairece.api.features.request.domain.dto.request.RequestPostRequestDto;
import br.edu.ifba.conectairece.api.features.request.domain.model.Request;
import br.edu.ifba.conectairece.api.features.update.domain.dto.response.UpdateResponseDTO;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Defines operations for managing {@link Request} entities.
 * Provides methods for saving, updating, retrieving, and deleting requests.
 *
 * @author Caio, Giovane Neves
 */

public interface RequestIService {
  RequestResponseDto save(RequestPostRequestDto dto);

  RequestResponseDto update(UUID id, RequestPostRequestDto dto);

  List<RequestResponseDto> findAll();

  RequestResponseDto findById(UUID id);

  void delete(UUID id);

  Page<MonitoringResponseDTO> getMonitoringListByRequestId(UUID id, Pageable pageable);

  Page<UpdateResponseDTO> getUpdateListByRequestId(UUID id, Pageable pageable);

  /**
   * Retrieves a paginated list of requests filtered by a specific type.
   *
   * @param type The type to filter requests by (e.g., "Solicitação de Alvará de Construção").
   * @param pageable Pagination and sorting information.
   * @return A Page containing RequestResponseDto objects matching the specified type.
   * @author Caio Alves
   */
  Page<RequestResponseDto> findByType(String type, Pageable pageable);

  /**
   * Retrieves a paginated list of Requests filtered by the latest recorded status (newStatus)
   * in the associated StatusHistory.
   * <p>
   * This method utilizes a query derivation based on the relationship path
   * (Request -> StatusHistory -> newStatus) to fetch filtered data efficiently.
   *
   * @param statusHistoryNewStatus The target status (e.g., "APPROVED", "PENDING") to filter the requests by.
   * @param pageable Pagination and sorting criteria.
   * @return A {@link Page} of {@link RequestResponseDto} containing the filtered and paginated results.
   */
  Page<RequestResponseDto> findAllByStatusHistory_NewStatus(String statusHistoryNewStatus, Pageable pageable);

  /**
   * Retrieves a paginated list of Requests that have reached a final status (COMPLETE or REJECTED).
   *
   * @param pageable Pagination and sorting criteria.
   * @return A Page of RequestResponseWithDetailsDTO containing final-status requests.
   */
  public Page<RequestResponseWithDetailsDTO> findAllFinalizedRequests(Pageable pageable);

  List<DocumentWithStatusResponseDTO> findApprovedDocumentsByRequestId(UUID requestId);
  List<DocumentWithStatusResponseDTO> findAllDocumentsByRequestId(UUID requestId);

}
