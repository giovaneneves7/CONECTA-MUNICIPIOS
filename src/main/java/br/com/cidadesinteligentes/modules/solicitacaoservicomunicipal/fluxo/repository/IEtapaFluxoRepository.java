package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.repository;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.Fluxo;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.EtapaFluxo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositório responsável pelo acesso às etapas de um fluxo.
 *
 * @author:  Giovane Neves, Andesson Reis
 */
@Repository
public interface IEtapaFluxoRepository extends JpaRepository<EtapaFluxo, UUID> {

    Optional<EtapaFluxo> findByFluxoIdAndEtapaId(UUID fluxoId, UUID etapaId);

    UUID fluxo(Fluxo fluxo);

    Optional<EtapaFluxo> findFirstByFluxoOrderByOrdemEtapaAsc(Fluxo fluxo);

    @Query("""
           SELECT ef 
           FROM EtapaFluxo ef 
           WHERE ef.fluxo.id = :fluxoId 
             AND ef.ordemEtapa = :ordem + 1
           """)
    Optional<EtapaFluxo> buscarProximaEtapa(
            @Param("fluxoId") UUID fluxoId,
            @Param("ordem") Long ordem
    );

    void deleteAllByFluxo(Fluxo fluxo);
    @Modifying
    @Query("DELETE FROM EtapaFluxo ef WHERE ef.fluxo.id = :fluxoId")
    void deleteAllByFluxoId(@Param("fluxoId") UUID fluxoId);


}
