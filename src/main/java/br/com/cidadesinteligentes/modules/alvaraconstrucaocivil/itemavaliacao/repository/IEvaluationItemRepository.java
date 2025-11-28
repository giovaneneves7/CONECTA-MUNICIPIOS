package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.repository;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.model.EvaluationItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository interface for managing {@link EvaluationItem} entities.
 *
 * @author Jorge Roberto
 */
public interface IEvaluationItemRepository extends JpaRepository<EvaluationItem, UUID> {
    /**
     * Retrieves a paginated list of evaluation items associated with a specific Document ID.
     * <p>
     * The query is automatically derived from the method name (Query Method).
     *
     * @param documentId The ID of the document whose items are being searched.
     * @param pageable Pagination and sorting information.
     * @return A {@link Page} of EvaluationItem entities.
     */
    Page<EvaluationItem> findAllByDocumentId(UUID documentId, Pageable pageable);
}
