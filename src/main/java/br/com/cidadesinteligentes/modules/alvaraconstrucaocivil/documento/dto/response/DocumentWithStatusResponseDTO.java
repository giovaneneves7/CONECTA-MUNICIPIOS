package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.dto.response;

import java.util.UUID;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.enums.DocumentStatus;

public record DocumentWithStatusResponseDTO(
    UUID id,
    String name,
    String fileExtension,
    String fileUrl,
    DocumentStatus status
) {}