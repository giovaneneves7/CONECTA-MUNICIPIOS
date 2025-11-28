package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.repository;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.model.Update;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Giovane Neves
 */
@Repository
public interface IUpdateRepository extends JpaRepository<Update, UUID> {

    /**
     * Get all updates linked to the request id passed as a parameter
     *
     * @author Giovane Neves
     * @param requestId The request id
     * @param pageable Pageable object
     * @return List of Update
     */
    @Query(
            value = "SELECT u FROM Update u WHERE u.request.id = :requestId",
            countQuery = "SELECT COUNT(u) FROM Update u WHERE u.request.id = :requestId"
    )
    Page<Update> findAllByRequestId(@Param("requestId") UUID requestId, Pageable pageable);

}
