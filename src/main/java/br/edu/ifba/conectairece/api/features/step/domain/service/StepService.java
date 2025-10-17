package br.edu.ifba.conectairece.api.features.step.domain.service;

import br.edu.ifba.conectairece.api.features.step.domain.dto.response.StepResponseDTO;
import br.edu.ifba.conectairece.api.features.step.domain.model.Step;
import br.edu.ifba.conectairece.api.features.step.domain.repository.IStepRepository;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
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
