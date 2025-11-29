package br.com.cidadesinteligentes.modules.core.gestaousuario.admin.repository;


import br.com.cidadesinteligentes.modules.core.gestaousuario.admin.model.AdminProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Data access repository fot the {@link AdminProfile} entity.
 *
 * @author Jorge Roberto
 */
public interface IAdminProfileRepository extends JpaRepository<AdminProfile, UUID> {
}
