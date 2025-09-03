package br.edu.ifba.conectairece.api.features.category.domain.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for representing Category data in API responses.
 * Contains the category identifier, name, description, and image URL.
 *
 * @author Caio Alves, Jorge Roberto
 */

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto implements Serializable{

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    @JsonProperty("imageUrl")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String imageUrl;
}
