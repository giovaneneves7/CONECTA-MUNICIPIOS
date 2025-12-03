package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

/**
 * Representa os dados necessários para criação de um fluxo associado
 * a um serviço municipal específico.
 *
 * @author: Giovane Neves, Andesson Reis
 */
public record FluxoRequestDTO(

        @JsonProperty("nome")
        String nome,

        @NotNull(message = "O campo 'servicoMunicipalId' é obrigatório.")
        @JsonProperty("servicoMunicipalId")
        Long servicoMunicipalId
) {}
