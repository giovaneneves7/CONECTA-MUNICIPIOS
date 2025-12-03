package br.com.cidadesinteligentes.config.tests;

import br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.model.Permissao;
import br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.repository.IPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

/**
 * Class for tests
 */
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final IPermissionRepository permissionRepository;

    @Override
    public void run(String... args) throws Exception {
        // Permission list
        List<String> requiredPermissions = Arrays.asList(
                "REGISTER_CITIZEN", "CREATE_PUBLIC_SERVANT", "SUBMIT_LICENSE_REQUEST",
                "VIEW_ALL_LICENSES", "REVIEW_LICENSE_REQUEST", "ISSUE_LICENSE",
                "REJECT_LICENSE_REQUEST", "MANAGE_ROLES", "DEACTIVATE_USER", "VIEW_SYSTEM_LOGS"
        );

        requiredPermissions.forEach(nome -> {
            //avoid duplication
            if (permissionRepository.findByNome(nome).isEmpty()) {
                Permissao permission = new Permissao();
                permission.setNome(nome);
                permissionRepository.save(permission);
                System.out.println("Permissão criada: " + nome);
            }
        });
    }
}
