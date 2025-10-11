package br.edu.ifba.conectairece.api.features.permission.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record PermissionRequestAddDTO(

        @JsonProperty("permissionName") @NotBlank
        String permissionName

) {
}
