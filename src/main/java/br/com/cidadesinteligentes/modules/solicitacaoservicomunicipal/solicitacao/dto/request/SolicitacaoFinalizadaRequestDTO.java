package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.request;

import java.util.UUID;
import java.util.List;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.enums.SolicitacaoStatus;

public record SolicitacaoFinalizadaRequestDTO(
    UUID solicitacaoId, 
    
    List<SolicitacaoStatus> status
) {}