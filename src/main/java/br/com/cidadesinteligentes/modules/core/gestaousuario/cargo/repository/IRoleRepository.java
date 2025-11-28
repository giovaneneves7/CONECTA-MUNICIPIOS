package br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.repository;

import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Data access repository for the {@link Role} entity.
 *
 * @author Jorge Roberto
 */
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
