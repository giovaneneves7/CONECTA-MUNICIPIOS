package br.edu.ifba.conectairece.api.features.step.domain.repository;

import br.edu.ifba.conectairece.api.features.step.domain.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IStepRepository extends JpaRepository<Step, UUID> {

    @Query("""
        SELECT s 
        FROM Step s
        JOIN FlowStep fs ON fs.step = s
        WHERE fs.flow.id = :flowId
        ORDER BY fs.order ASC
    """)
    List<Step> findAllByFlowId(UUID id);


}
