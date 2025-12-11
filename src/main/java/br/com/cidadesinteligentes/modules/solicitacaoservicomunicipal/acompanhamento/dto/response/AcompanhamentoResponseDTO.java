package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.response;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.enums.AcompanhamentoStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Objeto de Transferência de Dados (DTO) para representar os dados de Acompanhamento nas respostas da API.
 *
 * <p>Contém detalhes sobre o status de monitoramento de uma solicitação de serviço ao cidadão,
 * incluindo identificadores, código de rastreio e a situação atual.</p>
 *
 * @author Giovane Neves, Andesson Reis
 */
public record AcompanhamentoResponseDTO(
    @JsonProperty("id")
    UUID id,

    @JsonProperty("codigo")
    String codigo,

    @JsonProperty("atividadeId")
    UUID atividadeId,

    @JsonProperty("status")
    AcompanhamentoStatus status
) { }