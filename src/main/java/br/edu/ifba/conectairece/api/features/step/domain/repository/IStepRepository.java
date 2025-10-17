package br.edu.ifba.conectairece.api.features.step.domain.repository;

import br.edu.ifba.conectairece.api.features.step.domain.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author Giovane Neves
 */
@Repository
public interface IStepRepository extends JpaRepository<Step, UUID> {

    @Query("""
        SELECT s 
        FROM Step s
        JOIN FlowStep fs ON fs.step = s
        WHERE fs.flow.id = :flowId
        ORDER BY fs.stepOrder ASC
    """)
    List<Step> findAllByFlowId(@Param("flowId") UUID flowId);


}
