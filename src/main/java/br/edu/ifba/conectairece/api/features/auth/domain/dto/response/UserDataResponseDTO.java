package br.edu.ifba.conectairece.api.features.auth.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.edu.ifba.conectairece.api.features.auth.domain.enums.UserStatus;

import java.util.UUID;

public record UserDataResponseDTO(

	@JsonProperty("id")
    UUID id,

	@JsonProperty("username")
    String username,

    @JsonProperty("email")
    String email
){}