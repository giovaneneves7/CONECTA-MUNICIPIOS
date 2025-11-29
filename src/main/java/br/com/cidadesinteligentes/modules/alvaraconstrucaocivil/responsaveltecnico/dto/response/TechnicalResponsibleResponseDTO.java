package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.dto.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object for representing TechnicalResponsible data in API responses.
 * Contains the identifier, registration ID and type.
 *
 * @author Caio Alves
 */

public record TechnicalResponsibleResponseDTO(
    @JsonProperty(value = "id")
    UUID id,

    @JsonProperty(value = "registrationId")
    String registrationId,

    @JsonProperty(value = "responsibleType")
    String responsibleType,

    @JsonProperty(value = "imageUrl")
    String imageUrl,

    @JsonProperty(value = "responsibleName")
    String responsibleName,

    @JsonProperty("cpf")
    String cpf,

    @JsonProperty("email")
    String email,

    @JsonProperty("phone")
    String phone
) {

}
