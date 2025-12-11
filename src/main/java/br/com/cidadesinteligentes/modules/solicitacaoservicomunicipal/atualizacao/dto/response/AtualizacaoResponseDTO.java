package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * Objeto de Transferência de Dados (DTO) para representar uma Atualização nas respostas da API.
 *
 * @author Giovane Neves, Andesson Reis
 *
 * @param dataHora A data e hora em que a atualização ocorreu.
 * @param observacao A nota ou observação registrada na atualização.
 */
public record AtualizacaoResponseDTO(

        @JsonProperty("dataHora")
        LocalDateTime dataHora,

        @JsonProperty("observacao")
        String observacao

) {
}