package br.edu.ifba.conectairece.api.features.municipalservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifba.conectairece.api.features.municipalservice.domain.model.MunicipalService;

/**
 * Data access repository for the {@link MunicipalService} entity.
 * Provides CRUD operations and database interaction methods for municipal services.
 *
 * @author Caio Alves
 */

@Repository
public interface MunicipalServiceRepository extends JpaRepository<MunicipalService, Long>{

}
