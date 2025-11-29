package br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for receiving permission data in API **update** requests.
 * <p>
 * This DTO is used in PATCH/PUT operations to modify permission-related data
 * (such as adding a permission to a role).
 * <p>
 * This DTO mirrors the {@link PermissionRequestAddDTO} to ensure
 * compatibility with the service layer and validation.
 *
 * @author Andesson Reis
 */
public record PermissionRequestUpdateDTO(

        /**
         * The name of the permission to be added or modified.
         */
        @JsonProperty("permissionName")
        @NotBlank
        String permissionName
) {
}