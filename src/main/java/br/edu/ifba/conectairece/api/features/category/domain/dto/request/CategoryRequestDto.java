package br.edu.ifba.conectairece.api.features.category.domain.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for receiving Category data in API requests.
 * Used when creating or updating category information, including
 * name, description, and an optional image URL.
 *
 * @author Caio Alves, Jorge Roberto
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto implements Serializable {

    @JsonProperty("name")
    @NotNull(message = "Name is mandatory.")
    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("imageUrl")
    private String imageUrl;
}
