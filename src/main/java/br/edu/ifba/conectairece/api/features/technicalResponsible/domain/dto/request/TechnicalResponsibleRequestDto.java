package br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for receiving TechnicalResponsible data in API requests.
 * Used when creating or updating technical responsible information.
 *
 * @author Caio Alves
 */

public record TechnicalResponsibleRequestDto(

    @JsonProperty("registrationId")
    @NotNull(message = "Registration ID is mandatory.")
    @NotBlank(message = "Registration ID cannot be blank.")
    String registrationId,

    @JsonProperty("responsibleType")
    @NotNull(message = "Type is mandatory.")
    @NotBlank(message = "Type cannot be blank.")
    String ResponsibleType,

    @JsonProperty("imageUrl")
    String imageUrl,

    @JsonProperty("userId")
    @NotNull(message = "UserId is mandatory.")
    UUID userId
) {

}
