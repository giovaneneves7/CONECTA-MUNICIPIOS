package br.edu.ifba.conectairece.api.features.admin.domain.repository;


import br.edu.ifba.conectairece.api.features.admin.domain.model.AdminProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Data access repository fot the {@link AdminProfile} entity.
 *
 * @author Jorge Roberto
 */
public interface IAdminProfileRepository extends JpaRepository<AdminProfile, UUID> {
}
