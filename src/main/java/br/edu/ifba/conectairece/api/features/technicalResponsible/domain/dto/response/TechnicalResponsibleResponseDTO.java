package br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for representing technical responsible data in API responses.
 * Provides details about the professional assigned to a requirement.
 *
 * Contains:
 * - Identifier.
 * - Name.
 * - Email.
 * - Phone number.
 *
 * Author: Caio Alves
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TechnicalResponsibleResponseDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;
}
