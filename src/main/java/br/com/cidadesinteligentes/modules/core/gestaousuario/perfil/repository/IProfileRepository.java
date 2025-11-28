package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.repository;

import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
     * @param userId The user's id
     * @return A list of the user's linked profiles
     */
    @Query("SELECT p FROM Profile p WHERE p.user.id = :userId")
    Page<Profile> findAllByUserId(@Param("userId") UUID userId, Pageable pageable);


}
