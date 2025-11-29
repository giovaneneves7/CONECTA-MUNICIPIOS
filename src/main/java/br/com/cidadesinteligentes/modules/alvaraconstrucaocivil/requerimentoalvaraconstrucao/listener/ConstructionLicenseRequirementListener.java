package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.event.ConstructionLicenseRequirementCreatedEvent;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.model.ConstructionLicenseRequirement;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.request.RequestPostRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.service.IRequestService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConstructionLicenseRequirementListener {

    private final IRequestService requestService;

    @EventListener
        public void handleConstructionLicenseRequirementCreated(ConstructionLicenseRequirementCreatedEvent event) {
        ConstructionLicenseRequirement requirement = event.getRequirement();

        RequestPostRequestDTO requestDto = new RequestPostRequestDTO(
            requirement.getId().toString(),    
                            //    Check logic for estimating work completion
            requirement.getEndDate(),  
            requirement.getRequirementType().getName(),         
            "Request created automatically from ConstructionLicenseRequirement " + requirement.getId(),
            requirement.getSolicitante().getActiveProfile().getId(),    
            requirement.getMunicipalService().getId()            
        );

        requestService.save(requestDto);
    }
}
