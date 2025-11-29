package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.dto.response.StepResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.model.Step;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Giovane Neves
 */
public interface IStepService {

    StepResponseDTO createStep(Step step);
    List<StepResponseDTO> getAllSteps(Pageable pageable);

}
