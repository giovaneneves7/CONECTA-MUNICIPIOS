package br.edu.ifba.conectairece.api.features.flow.domain.repository;

import br.edu.ifba.conectairece.api.features.flow.domain.model.Flow;

import br.edu.ifba.conectairece.api.features.municipalservice.domain.model.MunicipalService;
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
