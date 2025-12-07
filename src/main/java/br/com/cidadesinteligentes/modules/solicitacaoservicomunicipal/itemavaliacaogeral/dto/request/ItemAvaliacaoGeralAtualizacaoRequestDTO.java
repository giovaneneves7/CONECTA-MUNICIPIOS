package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.dto.request;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.enums.StatusItemAvaliacaoGeral;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Objeto de Transferência de Dados (DTO) para a atualização de um Item de Avaliação Geral.
 *
 * @author Andesson Reis
 */
public record ItemAvaliacaoGeralAtualizacaoRequestDTO(

        @JsonProperty(value = "observacao")
        @NotBlank(message = "A observação é obrigatória")
        String observacao,

        @JsonProperty(value = "status")
        @NotNull(message = "O status é obrigatório")
        StatusItemAvaliacaoGeral status
) {
}