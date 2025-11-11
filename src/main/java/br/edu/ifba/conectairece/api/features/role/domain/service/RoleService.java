package br.edu.ifba.conectairece.api.features.role.domain.service;

import br.edu.ifba.conectairece.api.features.auth.domain.model.Permission;
import br.edu.ifba.conectairece.api.features.auth.domain.model.Role;
import br.edu.ifba.conectairece.api.features.auth.domain.repository.IRoleRepository;
import br.edu.ifba.conectairece.api.features.permission.domain.repository.IPermissionRepository;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class RoleService implements IRoleService{

    private final IPermissionRepository permissionRepository;
    private final IRoleRepository roleRepository;

    @Override @Transactional
    public void addPermission(String permissionName, Long roleId) {
        Permission permission = permissionRepository.findByName(permissionName)
            .orElseThrow(
                () -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()
            )
        );

        Role role = roleRepository.findById(roleId)
            .orElseThrow(
                () -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()
            )
        );

        Set<Permission> currentPermissions = role.getPermissions();

        if (!currentPermissions.add(permission)) {
            throw new BusinessException(BusinessExceptionMessage.PROFILE_ALREADY_HAS_THIS_PERMISSION.getMessage());
        }


        roleRepository.save(role);
    }

    @Override @Transactional
    public void removePermission(String permissionName, Long roleId) {
        Permission permission = permissionRepository.findByName(permissionName)
            .orElseThrow(
                () -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()
            )
        );

        Role role = roleRepository.findById(roleId)
            .orElseThrow(
                () -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()
            )
        );

        Set<Permission> currentPermissions = role.getPermissions();

        if (!currentPermissions.remove(permission)) {
            throw new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage());
        }

        role.setPermissions(currentPermissions);

        roleRepository.save(role);
    }
}
