package br.edu.ifba.conectairece.api.features.auth.domain.repository;

import br.edu.ifba.conectairece.api.features.auth.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data access repository for the {@link Role} entity.
 *
 * @author Jorge Roberto
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
