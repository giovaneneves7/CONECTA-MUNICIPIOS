package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Representa os dados completos de uma etapa pertencente a um fluxo,
 * incluindo nome, código, imagem associada e ordem dentro do fluxo.
 *
 * <p>Regras de Negócio:
 * <ul>
 *   <li>Uma etapa pertence a apenas um fluxo.</li>
 *   <li>As etapas são ordenadas para definir o progresso do fluxo.</li>
 * </ul>
 *
 * @author:
 *     Giovane Neves, Andesson Reis
 */
public record EtapaDadosCompletosResponseDTO(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("nome")
        String nome,

        @JsonProperty("codigo")
        String codigo,

        @JsonProperty("urlImagem")
        String urlImagem,

        @JsonProperty("ordem")
        long ordem

) {}
