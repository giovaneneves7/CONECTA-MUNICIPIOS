package br.edu.ifba.conectairece.api.features.profile.domain.repository;

import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import br.edu.ifba.conectairece.api.features.profile.domain.repository.projection.ProfileProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Data access repository for the {@link Profile} entity.
 *
 * @author Jorge Roberto
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Page<ProfileProjection> findAllProjectedBy(Pageable pageable);
}
