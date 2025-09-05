package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.model.ConstructionLicenseRequirement;

/**
 * Data access repository for the {@link ConstructionLicenseRequirement} entity.
 * Provides CRUD operations and database interaction methods 
 * for construction license requirements.
 *
 * This repository is responsible for persisting and retrieving 
 * construction-related requirement records, 
 * including their associations with documents and technical responsibles.
 *
 * Author: Caio Alves
 */

@Repository
public interface ConstructionLicenseRequirementRepository extends JpaRepository<ConstructionLicenseRequirement, Integer>{

}
