package br.edu.ifba.conectairece.api.features.publicservantprofile.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PublicServantRegisterResponseDTO(
        @JsonProperty("employeeId")
        String employeeId
) {
}
