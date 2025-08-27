package br.edu.ifba.conectairece.api.features.municipal_service.domain.dto.response;

import java.util.List;

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

     private Integer id;
    private String name;
    private String description;
    private List<CategoryResponseDto> categories;
}
