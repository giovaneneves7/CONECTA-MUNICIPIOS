package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.listener;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.event.ConstructionLicenseRequirementCreatedEvent;
import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.model.ConstructionLicenseRequirement;
import br.edu.ifba.conectairece.api.features.request.domain.dto.request.RequestPostRequestDto;
import br.edu.ifba.conectairece.api.features.request.domain.service.RequestIService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConstructionLicenseRequirementListener {

    private final RequestIService requestService;

    @EventListener
        public void handleConstructionLicenseRequirementCreated(ConstructionLicenseRequirementCreatedEvent event) {
        ConstructionLicenseRequirement requirement = event.getRequirement();

        RequestPostRequestDto requestDto = new RequestPostRequestDto(
            requirement.getId().toString(),    
                            //    Check logic for estimating work completion
            LocalDateTime.now().plusDays(30),  
            requirement.getRequirementType().getName(),         
            "Request created automatically from ConstructionLicenseRequirement " + requirement.getId(), 
            requirement.getTechnicalResponsible().getId(),        
            requirement.getMunicipalService().getId()            
        );

        requestService.save(requestDto);
    }
}
