package br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.repository;

import br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IPermissionRepository extends JpaRepository<Permission, UUID> {
    Optional<Permission> findByName(String name);
}
