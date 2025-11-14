package br.edu.ifba.conectairece.api.features.category.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Data Transfer Object for representing Category data in API responses.
 * Contains the category identifier, name, description, and image URL.
 *
 * @author Caio Alves, Jorge Roberto, Giovane Neves
 */
public record CategoryResponseDTO (

    @JsonProperty("id")
    Integer id,

    @JsonProperty("name")
    String name,

    @JsonProperty("description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String description,

    @JsonProperty("imageUrl")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String imageUrl
){}
