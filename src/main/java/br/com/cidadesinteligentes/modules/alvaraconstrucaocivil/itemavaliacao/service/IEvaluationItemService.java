package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.service;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.dto.response.EvaluationItemResponseDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.model.EvaluationItem;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing business logic related to {@link EvaluationItem} entities.
 *
 * @author Jorge Roberto
 */
public interface IEvaluationItemService {

    /**
     * Saves a new evaluation item and associates it with the specified document.
     *
     * @param documentId The ID of the document to associate the item with.
     * @param obj The evaluation item entity containing note and status details.
     * @return The saved item details as a DTO.
     * @throws BusinessException if the document is not found.
     */
    EvaluationItemResponseDTO save(UUID documentId, EvaluationItem obj);

    /**
     * Updates an existing evaluation item by its ID.
     *
     * @param evaluationId The ID of the evaluation item to update.
     * @param obj The entity containing the updated note and status details.
     * @throws BusinessException if the evaluation item is not found.
     */
    void update(UUID evaluationId, EvaluationItem obj);

    /**
     * Retrieves a paginated list of evaluation items associated with a specific document.
     *
     * @param documentId The ID of the document to retrieve items for.
     * @param pageable Pagination and sorting criteria.
     * @return A list of {@link EvaluationItemResponseDTO}.
     * @throws BusinessException if the document is not found.
     */
    List<EvaluationItemResponseDTO> findAllByDocumentId(UUID documentId, Pageable pageable);
}
