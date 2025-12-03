package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.repository;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.model.Etapa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author Giovane Neves, Andesson Reis
 */
@Repository
public interface IEtapaRepository extends JpaRepository<Etapa, UUID> {

    @Query("""
        SELECT s 
        FROM Etapa s
        JOIN EtapaFluxo fs ON fs.etapa = s
        WHERE fs.fluxo.id = :fluxoId
        ORDER BY fs.ordemEtapa ASC
    """)
    List<Etapa> findAllByFlowId(@Param("fluxoId") UUID fluxoId);


}
