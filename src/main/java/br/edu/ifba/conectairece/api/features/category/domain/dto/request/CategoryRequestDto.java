package br.edu.ifba.conectairece.api.features.category.domain.dto.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for receiving Category data in API requests.
 * Used when creating or updating category information, including
 * name, description, and an optional image URL.
 *
 * @author Caio Alves
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto implements Serializable {

    private String name;
    private String description;
    private String imageUrl;
}
