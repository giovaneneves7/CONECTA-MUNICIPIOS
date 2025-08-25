package br.edu.ifba.conectairece.api.features.profile.domain.repository;

import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Data access repository for the {@link Profile} entity.
 *
 * @author Jorge Roberto
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
