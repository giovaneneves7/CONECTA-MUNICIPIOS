package br.edu.ifba.conectairece.api.features.category.domain.dto.response;

/**
 * Lightweight representation of a Category entity.
 *
 * <p>Provides only the basic identifier, typically used in simplified
 * response payloads where full Category details are not required.</p>
 *
 * @author Andesson Reis
 */
public record CategorySimpleResponseDTO(Integer id) {
}
