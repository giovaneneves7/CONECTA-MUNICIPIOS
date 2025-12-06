package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.repository;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.model.Atualizacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Giovane Neves, Andesson Reis
 */
@Repository
public interface IAtualizacaoRepository extends JpaRepository<Atualizacao, UUID> {

    /**
     * Obtém todas as atualizações vinculadas ao ID da solicitação passado como parâmetro.
     *
     * @author Giovane Neves
     * @param solicitacaoId O ID da solicitação.
     * @param pageable Objeto de paginação.
     * @return Uma página de Atualizações.
     */
    @Query(
            value = "SELECT a FROM Atualizacao a WHERE a.solicitacao.id = :solicitacaoId",
            countQuery = "SELECT COUNT(a) FROM Atualizacao a WHERE a.solicitacao.id = :solicitacaoId"
    )
    Page<Atualizacao> findBySolicitacaoId(@Param("solicitacaoId") UUID solicitacaoId, Pageable pageable);

}