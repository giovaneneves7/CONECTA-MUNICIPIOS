package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Representa os dados necessários para registrar uma etapa dentro de um fluxo,
 * incluindo o identificador do fluxo, o identificador da etapa e a ordem
 * em que esta etapa deve ser executada.
 *
 * @author: Giovane Neves, Andesson Reis
 */
public record EtapaFluxoRequestDTO(

        @JsonProperty("fluxoId")
        @NotNull(message = "O campo 'fluxoId' é obrigatório.")
        UUID fluxoId,

        @JsonProperty("etapaId")
        @NotNull(message = "O campo 'etapaId' é obrigatório.")
        UUID etapaId,

        @JsonProperty("ordemEtapa")
        @NotNull(message = "O campo 'ordemEtapa' é obrigatório.")
        long ordemEtapa
) {}
