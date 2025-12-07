package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.PerfilComCargoResponseDTO;

public record UsuarioCompletoResponseDTO(

     @JsonProperty("content") 
    UsuarioDetalheResponseDTO content,
    @JsonProperty("profiles") 
    List<PerfilComCargoResponseDTO> profiles
) {

}
