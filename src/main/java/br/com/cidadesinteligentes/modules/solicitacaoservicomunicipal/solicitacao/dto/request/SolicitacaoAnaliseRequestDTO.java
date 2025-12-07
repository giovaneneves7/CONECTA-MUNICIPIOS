package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.request;

import java.util.UUID;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.enums.AssociationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SolicitacaoAnaliseRequestDTO(
    @NotNull(message = "O ID da solicitação (UUID) é obrigatório.")
    UUID solicitacaoId,

    @NotNull(message = "O ID do requerimento (Long) é obrigatório.")
    Long constructionLicenseRequirementId, 

    @NotBlank(message = "O Registration ID é obrigatório.")
    String registrationId,

    @NotNull(message = "O Status da análise é obrigatório")
    AssociationStatus status, 

    String justification 
) {}