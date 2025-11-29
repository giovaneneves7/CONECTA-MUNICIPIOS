package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.itemavaliacao.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Lightweight response DTO for an Evaluation Item entity.
 *
 * <p>Provides only the unique identifier, typically used in simplified
 * responses or nested resource structures where full entity details
 * are not required.</p>
 *
 * @author Andesson Reis
 */
public record EvaluationItemSimpleResponseDTO(
        @JsonProperty(value = "id")
        UUID id
) {
}
