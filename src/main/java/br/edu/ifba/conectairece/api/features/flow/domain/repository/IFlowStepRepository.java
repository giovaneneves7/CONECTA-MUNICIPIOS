package br.edu.ifba.conectairece.api.features.flow.domain.repository;

import br.edu.ifba.conectairece.api.features.flow.domain.model.FlowStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Giovane Neves
 */
@Repository
public interface IFlowStepRepository extends JpaRepository<FlowStep, UUID> {
}
