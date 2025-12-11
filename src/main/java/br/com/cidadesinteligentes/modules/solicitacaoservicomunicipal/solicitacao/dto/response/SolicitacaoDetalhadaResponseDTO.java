package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Objeto de Transferência de Dados (DTO) para representar os dados detalhados de uma Solicitação nas respostas da API.
 *
 * <p>Contém detalhes sobre a solicitação de serviço de um cidadão, incluindo
 * número de protocolo, datas de criação e atualização, data estimada de conclusão,
 * tipo, observações, status, nome completo e CPF do solicitante, além do serviço municipal associado.</p>
 *
 * @author Andesson Reis
 */
public record SolicitacaoDetalhadaResponseDTO(
        @JsonProperty("id")
        UUID id,

        @JsonProperty("numeroProtocolo")
        String numeroProtocolo,

        @JsonProperty("dataCriacao")
        LocalDateTime dataCriacao,

        @JsonProperty("dataPrevisaoConclusao")
        LocalDateTime dataPrevisaoConclusao,

        @JsonProperty("dataAtualizacao")
        LocalDateTime dataAtualizacao,

        @JsonProperty("tipo")
        String tipo,

        @JsonProperty("observacao")
        String observacao,

        @JsonProperty("servicoMunicipalId")
        Long servicoMunicipalId,

        @JsonProperty("status")
        String status,

        @JsonProperty("nomeCompletoSolicitante")
        String nomeCompletoSolicitante,

        @JsonProperty("cpfSolicitante")
        String cpfSolicitante
) {
}