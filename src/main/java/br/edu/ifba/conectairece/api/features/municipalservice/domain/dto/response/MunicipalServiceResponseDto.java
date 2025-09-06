package br.edu.ifba.conectairece.api.features.municipalservice.domain.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.edu.ifba.conectairece.api.features.category.domain.dto.response.CategoryResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for representing MunicipalService data in API responses.
 * Contains the municipal service identifier, name, description,
 * and its associated categories.
 *
 * @author Caio Alves
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MunicipalServiceResponseDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
    
    private List<CategoryResponseDto> categories;
}
