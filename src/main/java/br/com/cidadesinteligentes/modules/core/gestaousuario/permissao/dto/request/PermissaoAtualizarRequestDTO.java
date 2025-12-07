package br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

/*
* DTO para atualizar uma permissão
* */
public record PermissaoAtualizarRequestDTO(

        @JsonProperty("nome")
        @NotBlank(message = "Nome da Permissão é obrigatório")
        String nome
) {
}