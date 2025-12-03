package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * @author Giovane Neves, Andesson Reis
 */
public record FluxoEtapaResponseDTO(
    @JsonProperty("id")
    UUID id
){}
