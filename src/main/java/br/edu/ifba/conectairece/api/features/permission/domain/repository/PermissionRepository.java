package br.edu.ifba.conectairece.api.features.permission.domain.repository;

import br.edu.ifba.conectairece.api.features.auth.domain.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    Optional<Permission> findByName(String name);
}
