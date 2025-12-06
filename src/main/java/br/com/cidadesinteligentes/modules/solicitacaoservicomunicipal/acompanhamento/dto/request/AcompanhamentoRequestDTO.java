package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.request;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.enums.AcompanhamentoStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Objeto de Transferência de Dados (DTO) utilizado para receber dados de requisição
 * referentes à criação ou atualização de um Acompanhamento.
 *
 * @author Giovane Neves, Andesson Reis
 */
public record AcompanhamentoRequestDTO(

        @NotNull(message = "'solicitacaoId' é obrigatório")
        @JsonProperty("solicitacaoId")
        UUID solicitacaoId,

        @NotNull(message = "'atividadeId' é obrigatório")
        @JsonProperty("atividadeId")
        UUID atividadeId,

        @NotNull(message = "'codigo' é obrigatório")
        @NotBlank(message = "O código não pode estar em branco")
        @JsonProperty("codigo")
        String codigo,

        @NotNull(message = "'status' é obrigatório")
        @JsonProperty("status")
        AcompanhamentoStatus status
) {
}