package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Representa os dados necessários para registrar uma atividade dentro de um fluxo,
 * incluindo o identificador do fluxo, o identificador da atividade e a ordem
 * em que esta atividade deve ser executada.
 *
 * @author: Giovane Neves, Andesson Reis
 */
public record AtividadeFluxoRequestDTO(

        @JsonProperty("fluxoId")
        @NotNull(message = "O campo 'fluxoId' é obrigatório.")
        UUID fluxoId,

        @JsonProperty("atividadeId")
        @NotNull(message = "O campo 'atividadeId' é obrigatório.")
        UUID atividadeId,

        @JsonProperty("ordem")
        @NotNull(message = "O campo 'ordem' é obrigatório.")
        long ordem
) 
{}
