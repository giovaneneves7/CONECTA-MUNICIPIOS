package br.com.cidadesinteligentes.modules.core.gestaousuario.admin.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AdminUserContentResponseDTO(

    @JsonProperty("id")
    UUID id,

    @JsonProperty("type")
    String type,

    @JsonProperty("imageUrl")
    String imageUrl,

    @JsonProperty("name")
    String name,

    @JsonProperty("cpf")
    String cpf,

    @JsonProperty("phone")
    String phone,

    @JsonProperty("email")
    String email,

    @JsonProperty("gender")
    String gender,

    @JsonProperty("birthdate")
    LocalDate birthdate,

    @JsonProperty("status")
    String status
) {

}
