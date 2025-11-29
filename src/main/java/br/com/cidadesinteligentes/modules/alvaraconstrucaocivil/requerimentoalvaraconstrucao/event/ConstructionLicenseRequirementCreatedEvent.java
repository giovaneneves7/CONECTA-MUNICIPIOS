package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.event;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.model.ConstructionLicenseRequirement;

public class ConstructionLicenseRequirementCreatedEvent {

    private final ConstructionLicenseRequirement requirement; 

    public ConstructionLicenseRequirementCreatedEvent(ConstructionLicenseRequirement requirement) {
        this.requirement = requirement;
    }

    public ConstructionLicenseRequirement getRequirement() {
        return requirement;
    }
}

