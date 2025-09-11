package br.edu.ifba.conectairece.api.features.person.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for receiving person data in API requests.
 * Contains validation annotations to ensure data integrity.
 *
 * @author Jorge Roberto
 */
public record PersonRequestDTO (
    @JsonProperty("cpf")
    @Pattern(regexp = "\\d{11}", message = "CPF must contain exactly 11 digits.")
     String cpf,

    @JsonProperty("fullName")
    @NotNull(message = "Full name is mandatory.")
    @NotBlank(message = "Full name cannot be blank.")
     String fullName,

    @JsonProperty("birthDate")
    @NotNull(message = "Birth is mandatory.")
    @Past(message = "Birth date must be in the past")
     LocalDate birthDate,

    @JsonProperty("gender")
    @NotNull(message = "Gender is mandatory.")
    @NotBlank(message = "Gender cannot be blank.")
     String gender
) {}
