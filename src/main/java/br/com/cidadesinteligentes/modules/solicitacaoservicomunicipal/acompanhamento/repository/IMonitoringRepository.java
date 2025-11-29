package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.repository;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.enums.MonitoringStatus;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.model.Monitoring;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

/**
 * Data access repository for the {@link Monitoring} entity.
 * Provides CRUD operations and database interaction methods for service requests.
 *
 * @author Giovane Neves
 */
public interface IMonitoringRepository extends JpaRepository<Monitoring, UUID> {

    /**
     * Get all monitorings linked to the request id passed as a parameter
     *
     * @author Giovane Neves
     * @param requestId The request id
     * @param pageable Pageable object
     * @return List of Monitoring
     */
    @Query(
            value = "SELECT m FROM Monitoring m WHERE m.request.id = :requestId",
            countQuery = "SELECT COUNT(m) FROM Monitoring m WHERE m.request.id = :requestId"
    )
    Page<Monitoring> findAllByRequestId(@Param("requestId") UUID requestId, Pageable pageable);
    @Query("""
        SELECT m\s
        FROM Monitoring m\s
        WHERE m.request.id = :requestId\s
          AND m.monitoringStatus = :status
        ORDER BY m.createdAt DESC
   \s""")
    Optional<Monitoring> findFirstByRequestIdAndStatusOrderByCreatedAtDesc(
            @Param("requestId") UUID requestId,
            @Param("status") MonitoringStatus status
    );
}
