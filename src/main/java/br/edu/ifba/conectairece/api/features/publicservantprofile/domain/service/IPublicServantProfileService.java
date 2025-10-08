package br.edu.ifba.conectairece.api.features.publicservantprofile.domain.service;

import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.dto.response.PublicServantRegisterResponseDTO;
import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.model.PublicServantProfile;

import java.util.UUID;

public interface IPublicServantProfileService {
    PublicServantRegisterResponseDTO createPublicServantProfile (UUID userId, PublicServantProfile dto);
}
