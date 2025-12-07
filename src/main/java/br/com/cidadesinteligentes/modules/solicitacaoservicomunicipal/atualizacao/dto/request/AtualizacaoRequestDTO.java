package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Objeto de Transferência de Dados (DTO) para criar uma nova Atualização.
 *
 * @author Giovane Neves, Andesson Reis
 */
public record AtualizacaoRequestDTO(

        @NotNull(message = "'solicitacaoId' é obrigatório")
        @JsonProperty("solicitacaoId")
        UUID solicitacaoId,

        @NotNull(message = "'dataHora' é obrigatório")
        @JsonProperty("dataHora")
        LocalDateTime dataHora,

        @JsonProperty("observacao")
        String observacao
) {
}