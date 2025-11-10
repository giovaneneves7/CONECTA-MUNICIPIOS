package br.edu.ifba.conectairece.api.features.document.domain.dto.response;

import java.util.UUID;

import br.edu.ifba.conectairece.api.features.document.domain.enums.DocumentStatus;

public record DocumentWithStatusResponseDTO(
    UUID id,
    String name,
    String fileExtension,
    String fileUrl,
    DocumentStatus status
) {}