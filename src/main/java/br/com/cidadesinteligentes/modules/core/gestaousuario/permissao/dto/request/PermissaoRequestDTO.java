package br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

/*
 * DTO base de permissão, aonde tem os dados de Permissão
 * */

public record PermissaoRequestDTO(

        @JsonProperty("nome")
        @NotBlank(message = "Nome da Permissão é obrigatório")
        String nome

) {
}
