package br.edu.ifba.conectairece.api.features.publicservantprofile.domain.repository;

import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.model.PublicServantProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Data access repository for the {@link PublicServantProfile} entity.
 */
public interface PublicServantProfileRepository extends JpaRepository<PublicServantProfile, UUID> {
}
