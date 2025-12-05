package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UsuarioDetalheResponseDTO(

    @JsonProperty("id")
    UUID id,

    @JsonProperty("tipo")
    String tipo,

    @JsonProperty("imagemUrl")
    String imagemUrl,

    @JsonProperty("nome")
    String nome,

    @JsonProperty("cpf")
    String cpf,

    @JsonProperty("telefone")
    String telefone,

    @JsonProperty("email")
    String email,

    @JsonProperty("genero")
    String genero,

    @JsonProperty("dataNascimento")
    LocalDate dataNascimento,

    @JsonProperty("status")
    String status
) {

}
