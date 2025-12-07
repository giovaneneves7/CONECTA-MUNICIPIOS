package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Objeto de Transferência de Dados (DTO) para representar os dados de Solicitação nas respostas da API.
 *
 * <p>Contém detalhes sobre a solicitação de serviço de um cidadão, incluindo
 * número de protocolo, datas de criação e atualização, data estimada de conclusão,
 * tipo, observações e o serviço municipal associado.</p>
 *
 * @author Caio Alves, Giovane Neves
 */
public record SolicitacaoResponseDTO (
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
    Long servicoMunicipalId

){}