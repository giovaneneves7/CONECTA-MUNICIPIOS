package br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

public record TechnicalResponsibleRequestDTO (
        @JsonProperty("name")
        @NotNull(message = "Name is mandatory.")
        @NotBlank(message = "Name cannot be blank.")
        String name,

        @JsonProperty("email")
        @NotNull(message = "Email is mandatory.")
        @NotBlank(message = "Email cannot be blank.")
        @Email(message = "Invalid email format.")
        String email,

        @JsonProperty("phone")
        @NotNull(message = "Phone is mandatory.")
        @NotBlank(message = "Phone cannot be blank.")
        String phone
) {}
