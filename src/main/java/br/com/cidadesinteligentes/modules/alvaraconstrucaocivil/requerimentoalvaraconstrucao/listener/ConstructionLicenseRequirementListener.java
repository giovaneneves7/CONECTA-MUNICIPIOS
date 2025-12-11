package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.event.ConstructionLicenseRequirementCreatedEvent;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.model.ConstructionLicenseRequirement;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.request.SolicitacaoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.service.ISolicitacaoService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConstructionLicenseRequirementListener {

    private final ISolicitacaoService solicitacaoService;

    @EventListener
        public void handleConstructionLicenseRequirementCreated(ConstructionLicenseRequirementCreatedEvent event) {
        ConstructionLicenseRequirement requirement = event.getRequirement();

        SolicitacaoRequestDTO requestDto = new SolicitacaoRequestDTO(
            requirement.getId().toString(),    
                            //    Check logic for estimating work completion
            requirement.getEndDate(),  
            requirement.getRequirementType().getName(),         
            "Request created automatically from ConstructionLicenseRequirement " + requirement.getId(),
            requirement.getSolicitante().getTipoAtivo().getId(),
            requirement.getId()
        );

        solicitacaoService.save(requestDto);
    }
}
