package br.edu.ifba.conectairece.api.features.step.domain.repository;

import br.edu.ifba.conectairece.api.features.step.domain.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IStepRepository extends JpaRepository<Step, UUID> {



}
