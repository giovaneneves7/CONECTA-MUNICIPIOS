package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * DTO para pegar os dados de um Perfil
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PerfilResponseDTO(

    @JsonProperty("id")
    UUID id,

    @JsonProperty("tipo")
    String tipo,

    @JsonProperty("imagemUrl")
    String imagemUrl
){}
