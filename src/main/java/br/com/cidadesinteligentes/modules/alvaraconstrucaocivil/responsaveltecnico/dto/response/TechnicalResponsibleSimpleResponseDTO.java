package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.responsaveltecnico.dto.response;

import java.util.UUID;

/**
 * Minimal response DTO for a Technical Responsible entity.
 *
 * <p>Exposes only the unique identifier, intended for lightweight
 * responses or nested structures where full details are not required.</p>
 *
 * @author Andesson Reis
 */
public record TechnicalResponsibleSimpleResponseDTO(UUID id) {
}
