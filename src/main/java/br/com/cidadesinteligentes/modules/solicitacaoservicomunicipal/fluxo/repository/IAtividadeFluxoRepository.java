package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.repository;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.Fluxo;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.AtividadeFluxo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositório responsável pelo acesso às atividades de um fluxo.
 *
 * @author: Giovane Neves, Andesson Reis
 */
@Repository
public interface IAtividadeFluxoRepository extends JpaRepository<AtividadeFluxo, UUID> {

    Optional<AtividadeFluxo> findByFluxoIdAndAtividadeId(UUID fluxoId, UUID atividadeId);

    Optional<AtividadeFluxo> findFirstByFluxoOrderByOrdemAsc(Fluxo fluxo);
    List<AtividadeFluxo> findAllByFluxoOrderByOrdemAsc(Fluxo fluxo);

    @Query("""
           SELECT ef 
           FROM AtividadeFluxo ef 
           WHERE ef.fluxo.id = :fluxoId 
             AND ef.ordem = :ordem + 1
           """)
    Optional<AtividadeFluxo> buscarProximaAtividadePorFluxo(
            @Param("fluxoId") UUID fluxoId,
            @Param("ordem") Long ordem
    );

    void deleteAllByFluxo(Fluxo fluxo);

    @Modifying
    @Query("DELETE FROM AtividadeFluxo ef WHERE ef.fluxo.id = :fluxoId")
    void deleteAllByFluxoId(@Param("fluxoId") UUID fluxoId);

}