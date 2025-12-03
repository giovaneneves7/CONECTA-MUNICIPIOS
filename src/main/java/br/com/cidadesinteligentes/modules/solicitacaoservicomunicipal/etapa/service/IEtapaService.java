package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.dto.response.EtapaResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.model.Etapa;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Giovane Neves, Andesson Reis
 */
public interface IEtapaService {

    EtapaResponseDTO createStep(Etapa step);

    List<EtapaResponseDTO> getAllSteps(Pageable pageable);

}
