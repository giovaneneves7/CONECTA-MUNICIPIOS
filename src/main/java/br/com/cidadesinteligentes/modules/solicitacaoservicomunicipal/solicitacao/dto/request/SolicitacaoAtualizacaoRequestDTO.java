package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Objeto de Transferência de Dados (DTO) para o recebimento de dados de Solicitação nas requisições de **atualização** da API.
 * <p>
 * Utilizado na atualização de uma solicitação existente via PUT/PATCH, fornecendo número de protocolo,
 * data estimada de conclusão, tipo, observações e o ID do serviço municipal relacionado.
 * </p>
 * <p>
 * Este DTO espelha o {@link SolicitacaoRequestDTO} para garantir
 * a compatibilidade com a camada de serviço e validação.
 * </p>
 *
 * @author Andesson Reis
 */
public record SolicitacaoAtualizacaoRequestDTO (
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