package br.edu.ifba.conectairece.api.features.category.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for representing Category data in API responses.
 * Contains the category identifier, name, description, and image URL.
 *
 * @author Caio Alves
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {

     private Integer id;
    private String name;
    private String description;
    private String imageUrl;
}
