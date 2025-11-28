package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.repository;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.model.PublicServantProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Data access repository for the {@link PublicServantProfile} entity.
 * @author Jorge Roberto
 */
public interface IPublicServantProfileRepository extends JpaRepository<PublicServantProfile, UUID> {
}
