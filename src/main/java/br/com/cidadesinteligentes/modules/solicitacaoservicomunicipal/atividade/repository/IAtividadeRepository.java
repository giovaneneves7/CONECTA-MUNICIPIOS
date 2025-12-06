package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.model.Atividade;

import java.util.List;
import java.util.UUID;

/**
 * @author Giovane Neves, Andesson Reis
 */
@Repository
public interface IAtividadeRepository extends JpaRepository<Atividade, UUID> {

    @Query("""
                SELECT af.atividade
                FROM AtividadeFluxo af
                WHERE af.fluxo.id = :fluxoId
                ORDER BY af.ordem ASC
            """)
    List<Atividade> findAllByFluxoId(@Param("fluxoId") UUID fluxoId);

}
