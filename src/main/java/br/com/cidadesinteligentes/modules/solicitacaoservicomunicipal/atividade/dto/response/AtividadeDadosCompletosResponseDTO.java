package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Representa os dados completos de uma atividade pertencente a um fluxo,
 * incluindo nome, código, imagem associada e ordem dentro do fluxo.
 *
 * <p>Regras de Negócio:
 * <ul>
 *   <li>Uma atividade pertence a apenas um fluxo.</li>
 *   <li>As atividades são ordenadas para definir o progresso do fluxo.</li>
 * </ul>
 *
 * @author:
 *     Giovane Neves, Andesson Reis
 */
public record AtividadeDadosCompletosResponseDTO(

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
