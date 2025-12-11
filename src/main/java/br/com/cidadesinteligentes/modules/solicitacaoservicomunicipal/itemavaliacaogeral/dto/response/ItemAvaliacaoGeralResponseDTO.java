package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.dto.response;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.enums.StatusItemAvaliacaoGeral;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Objeto de Transferência de Dados (DTO) para a resposta de um Item de Avaliação Geral.
 *
 * @author Andesson Reis
 */
public record ItemAvaliacaoGeralResponseDTO(
        @JsonProperty("id")
        Long id,

        @JsonProperty("observacao")
        String observacao,

        @JsonProperty(value = "nome")
        String nome,

        @JsonProperty("status")
        StatusItemAvaliacaoGeral status
) {
}