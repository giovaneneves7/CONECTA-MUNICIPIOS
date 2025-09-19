package br.edu.ifba.conectairece.api.features.auth.domain.repository;

import br.edu.ifba.conectairece.api.features.auth.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Data access repository for the {@link Role} entity.
 *
 * @author Jorge Roberto
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
