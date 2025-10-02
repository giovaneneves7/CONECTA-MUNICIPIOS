package br.edu.ifba.conectairece.api.features.publicservantprofile.domain.service;

import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.model.PublicServantProfile;

import java.util.UUID;

public interface IPublicServantProfileService {
    void createPublicServantProfile (UUID userId, PublicServantProfile dto);
}
