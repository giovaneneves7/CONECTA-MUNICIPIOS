package br.edu.ifba.conectairece.api.features.profile.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) for profile **update** requests.
 * <p>
 * Used in PATCH/PUT operations, specifically for changing the active profile type.
 * <p>
 * This DTO mirrors the {@link ProfileRequestChangeProfileTypeDTO} to ensure
 * compatibility with the service layer and validation.
 *
 * @author Andesson Reis 
 */
public record ProfileRequestUpdateChangeProfileTypeDTO(
        /**
         * The new active profile type to set.
         */
        @JsonProperty("activeType")
        @NotBlank(message = "Active type cannot be blank.")
        String activeType,

        /**
         * The unique identifier (UUID) of the User
         * whose active profile type will be changed.
         */
        @JsonProperty("userId")
        @NotNull(message = "UserId is mandatory.")
        UUID userId
) {}
