package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.repository;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.enums.AcompanhamentoStatus;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.model.Acompanhamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

/**
 * Data access repository for the {@link Acompanhamento} entity.
 * Provides CRUD operations and database interaction methods for service requests.
 *
 * @author Giovane Neves, Andesson Reis
 */
public interface IAcompanhamentoRepository extends JpaRepository<Acompanhamento, UUID> {

    /**
     * Get all monitorings linked to the request id passed as a parameter
     *
     * @author Giovane Neves
     * @param requestId The request id
     * @param pageable Pageable object
     * @return List of Acompanhamento
     */
    @Query(
            value = "SELECT m FROM Acompanhamento m WHERE m.solicitacao.id = :solicitacaoId",
            countQuery = "SELECT COUNT(m) FROM Acompanhamento m WHERE m.solicitacao.id = :solicitacaoId"
    )
    Page<Acompanhamento> findAllBySolicitacaoId(@Param("solicitacaoId") UUID solicitacaoId, Pageable pageable);
    
        @Query("""
        SELECT m
        FROM Acompanhamento m
        WHERE m.solicitacao.id = :solicitacaoId
        AND m.status = :status
        ORDER BY m.dataCriacao DESC
        """)
        Optional<Acompanhamento> findFirstBySolicitacaoIdAndAcompanhamentoStatusOrderByDataCriacaoDesc(
                @Param("solicitacaoId") UUID solicitacaoId,
                @Param("status") AcompanhamentoStatus status
        );
}
