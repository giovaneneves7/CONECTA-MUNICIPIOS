package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.dto.response.StepResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.model.Step;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.repository.IStepRepository;
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
public class StepService implements IStepService{

    private final ObjectMapperUtil objectMapperUtil;
    private final IStepRepository stepRepository;

    @Override
    public StepResponseDTO createStep(final Step step) {

        return this.objectMapperUtil.mapToRecord(this.stepRepository.save(step),  StepResponseDTO.class);

    }

    @Override
    public List<StepResponseDTO> getAllSteps(Pageable pageable) {

        return this.stepRepository.findAll(pageable)
                .stream()
                .map(step -> this.objectMapperUtil.mapToRecord(step, StepResponseDTO.class))
                .toList();

    }
}
