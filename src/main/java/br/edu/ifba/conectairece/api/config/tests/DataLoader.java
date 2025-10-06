package br.edu.ifba.conectairece.api.config.tests;

import br.edu.ifba.conectairece.api.features.auth.domain.model.Permission;
import br.edu.ifba.conectairece.api.features.permission.domain.repository.PermissionRepository;
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

    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) throws Exception {
        // Permission list
        List<String> requiredPermissions = Arrays.asList(
                "REGISTER_CITIZEN", "CREATE_PUBLIC_SERVANT", "SUBMIT_LICENSE_REQUEST",
                "VIEW_ALL_LICENSES", "REVIEW_LICENSE_REQUEST", "ISSUE_LICENSE",
                "REJECT_LICENSE_REQUEST", "MANAGE_ROLES", "DEACTIVATE_USER", "VIEW_SYSTEM_LOGS"
        );

        requiredPermissions.forEach(name -> {
            //avoid duplication
            if (permissionRepository.findByName(name).isEmpty()) {
                Permission permission = new Permission();
                permission.setName(name);
                permissionRepository.save(permission);
                System.out.println("Permissão criada: " + name);
            }
        });
    }
}
