package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.repository;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.Flow;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.MunicipalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Giovane Neves
 */
@Repository
public interface IFlowRepository extends JpaRepository<Flow, UUID> {
    Flow findByMunicipalService(MunicipalService municipalService);
}
