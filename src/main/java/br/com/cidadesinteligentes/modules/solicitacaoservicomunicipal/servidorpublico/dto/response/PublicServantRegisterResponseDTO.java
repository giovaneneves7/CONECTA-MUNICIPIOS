package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PublicServantRegisterResponseDTO(
        @JsonProperty("employeeId")
        String employeeId
) {
}
