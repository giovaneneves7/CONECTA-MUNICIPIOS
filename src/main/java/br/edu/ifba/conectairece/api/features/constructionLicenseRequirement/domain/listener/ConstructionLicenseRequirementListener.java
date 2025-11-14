package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.event.ConstructionLicenseRequirementCreatedEvent;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.model.ConstructionLicenseRequirement;
import br.edu.ifba.conectairece.api.features.request.domain.dto.request.RequestPostRequestDTO;
import br.edu.ifba.conectairece.api.features.request.domain.service.IRequestService;
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
