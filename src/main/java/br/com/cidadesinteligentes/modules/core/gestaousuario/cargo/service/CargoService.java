package br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.model.Permissao;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.model.Cargo;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.repository.ICargoRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.repository.IPermissionRepository;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class CargoService implements ICargoService{

    private final IPermissionRepository permissionRepository;
    private final ICargoRepository cargoRepository;

    @Override @Transactional
    public void adicionarPermissao(String nomePermissao, Long idCargo) {
        Permissao permission = permissionRepository.findByNome(nomePermissao)
            .orElseThrow(
                () -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()
            )
        );

        Cargo cargo = cargoRepository.findById(idCargo)
            .orElseThrow(
                () -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()
            )
        );

        Set<Permissao> currentPermissions = cargo.getPermissoes();

        if (!currentPermissions.add(permission)) {
            throw new BusinessException(BusinessExceptionMessage.PROFILE_ALREADY_HAS_THIS_PERMISSION.getMessage());
        }


        cargoRepository.save(cargo);
    }

    @Override @Transactional
    public void removerPermissao(String nomePermissao, Long idCargo) {
        Permissao permission = permissionRepository.findByNome(nomePermissao)
            .orElseThrow(
                () -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()
            )
        );

        Cargo cargo = cargoRepository.findById(idCargo)
            .orElseThrow(
                () -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()
            )
        );

        Set<Permissao> currentPermissions = cargo.getPermissoes();

        if (!currentPermissions.remove(permission)) {
            throw new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage());
        }

        cargo.setPermissoes(currentPermissions);

        cargoRepository.save(cargo);
    }
}
