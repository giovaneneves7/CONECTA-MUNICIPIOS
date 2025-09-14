package br.edu.ifba.conectairece.api.features.request.domain.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifba.conectairece.api.features.request.domain.model.Request;

/**
 * Data access repository for the {@link Request} entity.
 * Provides CRUD operations and database interaction methods for service requests.
 *
 * @author Caio Alves, Giovane Neves
 */

@Repository
public interface RequestRepository extends JpaRepository<Request, UUID>{

    @Query("SELECT r FROM Request r WHERE r.profile.id = :profileId")
    Page<Request> findAllByProfileId(@Param("profileId") UUID profileId, Pageable pageable);
}
