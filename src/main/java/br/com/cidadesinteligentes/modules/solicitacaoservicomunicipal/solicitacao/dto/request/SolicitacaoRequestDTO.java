package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

/**
 * Objeto de Transferência de Dados (DTO) para o recebimento de dados de Solicitação nas requisições da API.
 * <p>
 * Utilizado na criação de uma nova solicitação, fornecendo número de protocolo,
 * data estimada de conclusão, tipo, observações e o ID do serviço municipal relacionado.
 * </p>
 *
 * @author Caio Alves, Andesson Reis
 */
public record SolicitacaoRequestDTO (
    @JsonProperty("numeroProtocolo")
    String numeroProtocolo,

    @NotNull(message = "A data de previsão de conclusão é obrigatória")
    @JsonProperty("dataPrevisaoConclusao")
    LocalDateTime dataPrevisaoConclusao,

    @JsonProperty("tipo")
    String tipo,

    @JsonProperty("observacao")
    String observacao,

    @JsonProperty("perfilId")
    UUID perfilId,

    @NotNull(message = "O ID do serviço municipal é obrigatório")
    @JsonProperty("servicoMunicipalId")
    Long servicoMunicipalId
) {}