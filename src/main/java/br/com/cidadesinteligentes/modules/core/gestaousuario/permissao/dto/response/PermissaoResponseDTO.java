package br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * DTO Padrão de resposta de permissão, aonde tem os atributos da classe
 * */
public record PermissaoResponseDTO(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("nome")
        String nome
) {
}
