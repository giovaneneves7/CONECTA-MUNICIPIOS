package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.dto.response.EtapaResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.model.Etapa;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.repository.IEtapaRepository;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Giovane Neves
 */

@Service
@RequiredArgsConstructor
public class EtapaService implements IEtapaService {

    private final ObjectMapperUtil objectMapperUtil;
    private final IEtapaRepository etapaRepository;

    @Override
    public EtapaResponseDTO createStep(final Etapa etapa) {

        return this.objectMapperUtil.mapToRecord(this.etapaRepository.save(etapa), EtapaResponseDTO.class);

    }

    @Override
    public List<EtapaResponseDTO> getAllSteps(Pageable pageable) {

        return this.etapaRepository.findAll(pageable)
                .stream()
                .map(etapa -> this.objectMapperUtil.mapToRecord(etapa, EtapaResponseDTO.class))
                .toList();

    }
}
