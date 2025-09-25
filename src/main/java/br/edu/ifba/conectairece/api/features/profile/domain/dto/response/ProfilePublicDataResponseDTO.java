package br.edu.ifba.conectairece.api.features.profile.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Data Transfer Object for representing Public Data of the User's profile in API responses.
 * Contains the profile identifier, name, description, image URL, type, phone, email and birthdate.
 *
 * @author Giovane Neves
 */
public record ProfilePublicDataResponseDTO(

    @JsonProperty("id")
    UUID id,

    @JsonProperty("type")
    String type,

    @JsonProperty("imageUrl")
    String imageUrl,

    @JsonProperty("name")
    String name,

    @JsonProperty("cpf")
    String cpf,

    @JsonProperty("phone")
    String phone,

    @JsonProperty("email")
    String email,

    @JsonProperty("gender")
    String gender,

    @JsonProperty("birthdate")
    LocalDate birthdate

) {
}
