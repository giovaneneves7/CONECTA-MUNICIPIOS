package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) for changing the active profile type. Contains validation annotations to ensure data integrity.
 *
 * @author Jorge Roberto
 */
public record ProfileRequestChangeProfileTypeDTO(
        @JsonProperty("activeType")
        @NotBlank(message = "Active type cannot be blank.")
        String activeType,

        @JsonProperty("userId")
        @NotNull(message = "UserId is mandatory.")
        UUID userId
) {}
