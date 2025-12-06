package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.dto.response.AtividadeDadosCompletosResponseDTO;

import java.util.List;
import java.util.UUID;

/**
 * Representa os dados completos de um fluxo, incluindo suas atividades
 * e o serviço municipal ao qual está vinculado.
 *
 * @author:
 *     Giovane Neves,
 *     Andesson Reis
 */
public record FluxoDadosCompletosResponseDTO(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("nome")
        String nome,

        @JsonProperty("atividades")
        List<AtividadeDadosCompletosResponseDTO> atividades,

        @JsonProperty("servicoMunicipalId")
        Long servicoMunicipalId

) {}
