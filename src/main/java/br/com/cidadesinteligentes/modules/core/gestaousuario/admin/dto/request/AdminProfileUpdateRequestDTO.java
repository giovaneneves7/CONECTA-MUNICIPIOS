package br.com.cidadesinteligentes.modules.core.gestaousuario.admin.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * DTO (Data Transfer Object) for the administrator profile **update** request.
 * <p>
 * This record is used to transport data received from the client
 * (via JSON) in the body of a PUT/PATCH request to the admin profile update endpoint.
 * <p>
 * Contains (Bean Validation) to ensure data integrity
 * before it reaches the service layer.
 *
 * @author Andesson Reis
 */
public record AdminProfileUpdateRequestDTO(
        /**
         * The type of the administrative profile
         * This field is mandatory for the update.
         */
        @JsonProperty(value = "type")
        @NotNull(message = "Type is mandatory.")
        @NotBlank(message = "Type cannot be blank.")
        String type,

        /**
         * The URL for the administrator's avatar image.
         * This field is optional; if null or blank, the existing value can be retained.
         */
        @JsonProperty(value = "imageUrl")
        String imageUrl,

        /**
         * The unique identifier (UUID) of the {@link br.edu.ifba.conectairece.api.features.user.domain.entity.Usuario}
         * to which this admin profile is linked.
         * <p>
         * This field is mandatory to identify which user (and, consequently,
         * which profile) should be updated.
         */
        @JsonProperty("userId")
        @NotNull(message = "UserId is mandatory.")
        UUID userId
) {
}