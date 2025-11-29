package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.tiporequerimento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.tiporequerimento.model.RequirementType;

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
public interface IRequirementTypeRepository extends JpaRepository<RequirementType, Long>{

}
