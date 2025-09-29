package br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.event;

import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.model.ConstructionLicenseRequirement;

public class ConstructionLicenseRequirementCreatedEvent {

    private final ConstructionLicenseRequirement requirement; 

    public ConstructionLicenseRequirementCreatedEvent(ConstructionLicenseRequirement requirement) {
        this.requirement = requirement;
    }

    public ConstructionLicenseRequirement getRequirement() {
        return requirement;
    }
}

