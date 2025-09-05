package br.edu.ifba.conectairece.api.features.requirementType.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifba.conectairece.api.features.requirementType.domain.model.RequirementType;

/**
 * Data access repository for the {@link RequirementType} entity.
 * Provides CRUD operations and database interaction methods 
 * for requirement types.
 *
 * This repository manages the persistence of requirement type definitions,
 * which classify and organize different kinds of requirements.
 *
 * Author: Caio Alves
 */

@Repository
public interface RequirementTypeRepository extends JpaRepository<RequirementType, Integer>{

}
