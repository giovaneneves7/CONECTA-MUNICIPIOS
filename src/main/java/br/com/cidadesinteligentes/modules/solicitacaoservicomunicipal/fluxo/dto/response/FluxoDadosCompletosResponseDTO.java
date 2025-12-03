package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.response;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.dto.response.EtapaDadosCompletosResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

/**
 * Representa os dados completos de um fluxo, incluindo suas etapas
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

        @JsonProperty("etapas")
        List<EtapaDadosCompletosResponseDTO> etapas,

        @JsonProperty("servicoMunicipalId")
        Long servicoMunicipalId

) {}
