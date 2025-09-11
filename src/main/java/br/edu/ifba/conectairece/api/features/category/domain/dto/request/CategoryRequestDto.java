package br.edu.ifba.conectairece.api.features.category.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for receiving Category data in API requests.
 * Used when creating or updating category information, including
 * name, description, and an optional image URL.
 *
 * @author Caio Alves, Jorge Roberto
 */
public record CategoryRequestDto (
    @JsonProperty("name")
    @NotNull(message = "Name is mandatory.")
    @NotBlank(message = "Name cannot be blank.")
    String name,

    @JsonProperty("description")
    String description,

    @JsonProperty("imageUrl")
    String imageUrl
) {}
