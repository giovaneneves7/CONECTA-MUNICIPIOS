package br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for receiving technical responsible data in API requests.
 * Used when assigning a professional to oversee a requirement.
 *
 * Contains:
 * - Name of the professional.
 * - Email address (validated for format).
 * - Phone number.
 *
 * Validation:
 * - All fields are mandatory and cannot be blank.
 *
 * Author: Caio Alves
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TechnicalResponsibleRequestDTO {

    @JsonProperty("name")
    @NotNull(message = "Name is mandatory.")
    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @JsonProperty("email")
    @NotNull(message = "Email is mandatory.")
    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Invalid email format.")
    private String email;

    @JsonProperty("phone")
    @NotNull(message = "Phone is mandatory.")
    @NotBlank(message = "Phone cannot be blank.")
    private String phone;
}
