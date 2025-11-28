package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.dto.response.PublicServantRegisterResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.model.PublicServantProfile;

import java.util.UUID;

public interface IPublicServantProfileService {
    PublicServantRegisterResponseDTO createPublicServantProfile (UUID userId, PublicServantProfile dto);
}
