package br.edu.ifba.conectairece.api.features.profile.domain.repository;

import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;


/**
 * Data access repository for the {@link Profile} entity.
 *
 * @author Jorge Roberto, Giovane Neves
 */
public interface IProfileRepository extends JpaRepository<Profile, UUID> {

    /**
     * Get all profiles linked to a user by the user's id passed as a parameter
     *
     * @author Giovane Neves
     * @param id The user's id
     * @return A list of the user's linked profiles
     */
    @Query("SELECT p FROM Profile p WHERE p.user.id = :userId")
    List<Profile> findAllByUserId(@Param("userId") UUID id);

}
