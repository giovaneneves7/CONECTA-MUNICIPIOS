package br.edu.ifba.conectairece.api.features.request.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifba.conectairece.api.features.request.domain.model.Request;

/**
 * Data access repository for the {@link Request} entity.
 * Provides CRUD operations and database interaction methods for service requests.
 *
 * @author Caio Alves
 */

@Repository
public interface RequestRepository extends JpaRepository<Request, UUID>{

}
