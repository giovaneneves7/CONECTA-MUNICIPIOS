package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

/**
 * DTO de resposta simplificado para uma entidade de Solicitação.
 *
 * <p>Contém apenas o identificador único, tornando-o adequado para
 * respostas compactas, referências ou estruturas de DTO aninhadas onde
 * os detalhes completos da solicitação não são necessários.</p>
 *
 * @author Andesson Reis
 */
public record SolicitacaoSimplesResponseDTO(
    @JsonProperty("id")
    UUID id
) {
}