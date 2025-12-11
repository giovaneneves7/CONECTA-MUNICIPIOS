package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.dto.request;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.enums.StatusItemAvaliacaoGeral;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Objeto de Transferência de Dados (DTO) para a criação de um Item de Avaliação Geral.
 *
 * @author Andesson Reis
 */
public record ItemAvaliacaoGeralRequestDTO(

        @JsonProperty(value = "solicitacaoId")
        @NotNull(message = "O ID da solicitação é obrigatório")
        UUID solicitacaoId,

        @JsonProperty(value = "observacao")
        @NotBlank(message = "A observação é obrigatória")
        String observacao,

        @JsonProperty(value = "nome")
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @JsonProperty(value = "status")
        @NotNull(message = "O status é obrigatório")
        StatusItemAvaliacaoGeral status
) {
}