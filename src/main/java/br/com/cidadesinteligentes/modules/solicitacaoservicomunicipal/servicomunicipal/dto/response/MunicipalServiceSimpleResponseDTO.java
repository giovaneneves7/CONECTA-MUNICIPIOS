package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.dto.response;

/**
 * Basic response structure for a Municipal Service entity.
 *
 * <p>Contains only the unique identifier, intended for compact responses
 * or nested DTO mappings where full service details are unnecessary.</p>
 *
 * @author Andesson Reis
 */
public record MunicipalServiceSimpleResponseDTO(Long id) {
}
