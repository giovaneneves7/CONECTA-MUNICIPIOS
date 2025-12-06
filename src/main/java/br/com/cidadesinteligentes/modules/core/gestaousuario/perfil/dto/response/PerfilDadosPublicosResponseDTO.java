package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO para representar os dados públicos de um perfil de algum usuário.
 *
 * @author Giovane Neves
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PerfilDadosPublicosResponseDTO(

    @JsonProperty("id")
    UUID id,

    @JsonProperty("tipo")
    String tipo,

    @JsonProperty("imagemUrl")
    String imagemUrl,

    @JsonProperty("nomeCompleto")
    String nomeCompleto,

    @JsonProperty("cpf")
    String cpf,

    @JsonProperty("telefone")
    String telefone,

    @JsonProperty("email")
    String email,

    @JsonProperty("genero")
    String genero,

    @JsonProperty("dataNascimento")
    LocalDate dataNascimento

) {
}
